package TYT.Project.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthorizationFilter filter) {
        return builder.routes()
                .route("auth-service-route", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthorizationFilter.Config())))
                        .uri("lb://auth-service"))
                .route("management-service-route", r -> r.path("/management-service/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthorizationFilter.Config(List.of("ADMIN")))))
                        .uri("lb://management-service"))
                .route("product-service-route", r -> r.path("/product-service/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthorizationFilter.Config(List.of("ADMIN")))))
                        .uri("lb://product-service"))
                .route("sales-service-route", r -> r.path("/sales-service/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthorizationFilter.Config(List.of("CASHIER")))))
                        .uri("lb://sales-service"))
                .route("record-service-route", r -> r.path("/record/**")
                        .filters(f -> f.filter(filter.apply(new JwtAuthorizationFilter.Config(List.of("MANAGER")))))
                        .uri("lb://record-service"))
                .build();
    }
}