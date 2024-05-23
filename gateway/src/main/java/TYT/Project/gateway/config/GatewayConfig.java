package TYT.Project.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("recordRoute", r -> r.path("/record/**")
                        .filters(f -> f.rewritePath("/record/(?<segment>.*)", "/record/create/${segment}"))
                        .uri("http://localhost:8083"))
                .route("userRoute", r -> r.path("/user/**")
                        .uri("http://localhost:8080"))
                .route("authRoute", r -> r.path("/auth/**")
                        .uri("http://localhost:8081"))
                .build();
    }
}
