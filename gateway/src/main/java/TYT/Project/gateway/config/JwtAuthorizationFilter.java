package TYT.Project.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Log4j2
@Component
@RefreshScope
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {

    private final SecretKey signingKey;
    private final String[] excludedPaths = {"/auth/login", "/product-service/product/all", "/product-service/product/{id}"};

    public JwtAuthorizationFilter(@Value("${security.jwt.secret-key}") String secretKey) {
        super(Config.class);
        this.signingKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "HmacSHA256");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = request.getPath().toString();

            if (isAuthRequired(requestPath)) {
                String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
                    return onError(exchange);
                }

                try {
                    Claims claims = extractClaimsFromToken(authorizationHeader.substring(7));

                    if (!hasRequiredRole(claims, config.getRoles())) {
                        return onForbidden(exchange);
                    }

                    List<String> userRoles = extractRolesFromClaims(claims);
                    ServerHttpRequest modifiedRequest = addUserInfoToHeader(exchange, claims, userRoles);

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());

                } catch (Exception e) {
                    log.error("JWT validation failed: {}", e.getMessage());
                    return onError(exchange, "Invalid or missing authorization token");
                }
            }
            return chain.filter(exchange);
        };
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isAuthRequired(String requestPath) {
        for (String path : excludedPaths) {
            if (requestPath.startsWith(path)) {
                return false;
            }
        }
        return true;
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private Mono<Void> onForbidden(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String s) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean hasRequiredRole(Claims claims, List<String> requiredRoles) {
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }

        List<String> roles = extractRolesFromClaims(claims);
        return roles.stream().anyMatch(requiredRoles::contains);
    }

    private List<String> extractRolesFromClaims(Claims claims) {
        return ((List<?>) claims.get("roles")).stream()
                .map(Object::toString)
                .toList();
    }

    private ServerHttpRequest addUserInfoToHeader(ServerWebExchange exchange, Claims claims, List<String> userRoles) {
        return exchange.getRequest().mutate()
                .header("userId", String.valueOf(claims.get("id")))
                .header("userRoles", String.join(",", userRoles))
                .build();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private List<String> roles;
    }
}