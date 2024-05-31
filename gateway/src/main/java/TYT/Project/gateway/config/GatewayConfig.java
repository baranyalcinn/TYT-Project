package TYT.Project.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This class is responsible for configuring the gateway routes for the microservices.
 * It uses Spring Cloud Gateway for routing and load balancing.
 */
@Configuration
public class GatewayConfig {

    
    /**
     * This method defines the routes for the microservices.
     * It uses a RouteLocatorBuilder to build the routes.
     * Each route is defined with a path, a filter, and a URI.
     * The path is the pattern that the incoming request should match.
     * The filter is applied to the request before it is routed.
     * The URI is the address of the microservice to which the request should be routed.
     * The URI uses the "lb" scheme for load balancing.
     *
     * @param builder a RouteLocatorBuilder used to build the routes
     * @param filter a JwtAuthorizationFilter used to apply authorization rules to the routes
     * @return a RouteLocator containing the routes
     */
    @Bean
    public RouteLocator microserviceRouter(RouteLocatorBuilder builder, JwtAuthorizationFilter filter) {
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