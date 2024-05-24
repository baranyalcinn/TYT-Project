package TYT.Project.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Log4j2
@Component
public class JwtAuthorizationFilter extends AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config> {

    private final SecretKey signingKey;

    public JwtAuthorizationFilter(@Value("${security.jwt.secret-key}") String secretKey) {
        super(Config.class);
        this.signingKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "HmacSHA256");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = request.getPath().toString();

            // Early exit if authentication is not required for this path
            if (!isAuthRequired(requestPath)) {
                return chain.filter(exchange);
            }

            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
                return onError(exchange);
            }

            String jwtToken = authorizationHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(signingKey)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                // Early exit if the user doesn't have the required roles
                if (!hasRequiredRole(claims, config.getRoles())) {
                    return onError(exchange);
                }

                // Extract user roles
                List<String> userRoles = extractRolesFromClaims(claims);

                // Set user information in request headers
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("userId", String.valueOf(claims.get("id")))
                        .header("userRoles", String.join(",", userRoles))
                        .build();

                // Continue with the filter chain
                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                log.error("JWT validation failed: {}", e.getMessage());
                return onError(exchange, "Invalid or missing authorization token");
            }
        };
    }

    private boolean isAuthRequired(String requestPath) {
        String[] excludedPaths = {"/auth/login", "/product/all", "/product/{id}"};

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

    private Mono<Void> onError(ServerWebExchange exchange, String s) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // Optionally set a message in the response body
        // return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
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
        List<String> userRoles = new ArrayList<>();
        Object rolesClaim = claims.get("roles");
        if (rolesClaim instanceof List<?> roles) {
            for (Object role : roles) {
                if (role instanceof String) {
                    userRoles.add((String) role);
                }
            }
        }
        return userRoles;
    }

    @Setter
    @Getter
    public static class Config {
        private List<String> roles;
    }
}