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

    private final String secretKey;


    public JwtAuthorizationFilter(@Value("${security.jwt.secret-key}") String secretKey) {
        super(Config.class);
        this.secretKey = secretKey;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestPath = request.getPath().toString();

            if (!isAuthRequired(requestPath)) {
                return chain.filter(exchange);
            }

            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
                return this.onError(exchange);
            }

            String jwtToken = authorizationHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                if (!hasRequiredRole(claims, config.getRoles())) {
                    return this.onError(exchange);
                }

                List<String> userRoles = new ArrayList<>();
                if (claims.get("roles") instanceof List<?> rolesClaim) {
                    for (Object role : rolesClaim) {
                        if (role instanceof String) {
                            userRoles.add((String) role);
                        }
                    }
                }

                String userRolesString = String.join(",", userRoles);

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("userId", String.valueOf(claims.get("id")))
                        .header("userRoles", userRolesString)
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            }  catch (Exception e) {
                log.error("JWT validation failed: {}", e.getMessage()); // Log the error
                return onError(exchange, "Invalid or missing authorization token");
            }
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String invalidOrMissingAuthorizationToken) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
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

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
    }

    private boolean hasRequiredRole(Claims claims, List<String> requiredRoles) {
        if (requiredRoles == null || requiredRoles.isEmpty()) {
            return true;
        }

        Object rolesClaim = claims.get("roles");
        if (rolesClaim instanceof List<?> roles) {
            for (Object role : roles) {
                if (role instanceof String && requiredRoles.contains(role)) {
                    return true;
                }
            }
        }
        return false; // No matching role found
    }

    @Setter
    @Getter
    public static class Config {
        private List<String> roles;

    }
}