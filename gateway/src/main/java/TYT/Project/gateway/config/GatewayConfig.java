package TYT.Project.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {


    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public GatewayConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service-route", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .route("management-service-route", r -> r.path("/management/**")
                        .uri("lb://management-service"))
                .route("product-service-route", r -> r.path("/product/**")
                        .uri("lb://product-service"))
                .route("sales-service-route", r -> r.path("/sales/**")
                        .uri("lb://sales-service"))
                .route("record-service-route", r -> r.path("/record/**")
                        .uri("lb://record-service"))
                .build();
    }
}