package TYT.Project.gateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class GatewayConfigTest {

    @Autowired
    private RouteLocator routeLocator;

    @Test
    void microserviceRouter_authServiceRouteExists() {
        assertTrue(Objects.requireNonNull(routeLocator.getRoutes().collectList().block()).stream().anyMatch(route -> route.getId().equals("auth-service-route")));
    }

    @Test
    void microserviceRouter_managementServiceRouteExists() {
        assertTrue(Objects.requireNonNull(routeLocator.getRoutes().collectList().block()).stream().anyMatch(route -> route.getId().equals("management-service-route")));
    }

    @Test
    void microserviceRouter_productServiceRouteExists() {
        assertTrue(Objects.requireNonNull(routeLocator.getRoutes().collectList().block()).stream().anyMatch(route -> route.getId().equals("product-service-route")));
    }

    @Test
    void microserviceRouter_salesServiceRouteExists() {
        assertTrue(Objects.requireNonNull(routeLocator.getRoutes().collectList().block()).stream().anyMatch(route -> route.getId().equals("sales-service-route")));
    }

    @Test
    void microserviceRouter_recordServiceRouteExists() {
        assertTrue(Objects.requireNonNull(routeLocator.getRoutes().collectList().block()).stream().anyMatch(route -> route.getId().equals("record-service-route")));
    }
}