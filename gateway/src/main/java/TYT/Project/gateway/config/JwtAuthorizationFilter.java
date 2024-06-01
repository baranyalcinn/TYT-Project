package TYT.Project.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RefreshScope
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_HEADER = "userId";
    private static final String USER_ROLES_HEADER = "userRoles";

    private final SecretKey signingKey;
    Set<String> excludedPaths = Set.of("/auth/login", "/product-service/product/all",
            "/product-service/product/{id}", "/product-service/product/paginated");

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
                String authorizationHeader = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
                if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(BEARER_PREFIX)) {
                    return onError(exchange, "Missing or invalid authorization header");
                }

                try {
                    String token = authorizationHeader.substring(BEARER_PREFIX.length());
                    Claims claims = extractClaimsFromToken(token);

                    if (!hasRequiredRole(claims, config.getRoles())) {
                        return onError(exchange, "Forbidden: Insufficient permissions");
                    }

                    return chain.filter(exchange.mutate()
                            .request(addUserInfoToHeader(request, claims))
                            .build());

                } catch (Exception e) {
                    log.error("JWT validation failed: {}", e.getMessage());
                    return onError(exchange, "Invalid or expired token");
                }
            }

            return chain.filter(exchange);
        };
    }

    protected Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isAuthRequired(String requestPath) {
        return excludedPaths.stream().noneMatch(requestPath::startsWith);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        log.error(message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.error(new RuntimeException(message));
    }

    private boolean hasRequiredRole(Claims claims, List<String> requiredRoles) {
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }

        List<String> userRoles = extractRolesFromClaims(claims);
        return userRoles.stream().anyMatch(requiredRoles::contains);
    }

    private List<String> extractRolesFromClaims(Claims claims) {
        return ((List<?>) claims.get("roles")).stream()
                .map(Object::toString)
                .toList();
    }

    private ServerHttpRequest addUserInfoToHeader(ServerHttpRequest request, Claims claims) {
        List<String> userRoles = extractRolesFromClaims(claims);
        return request.mutate()
                .header(USER_ID_HEADER, String.valueOf(claims.get("id")))
                .header(USER_ROLES_HEADER, String.join(",", userRoles))
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