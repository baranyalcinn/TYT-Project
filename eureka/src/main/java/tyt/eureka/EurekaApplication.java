package tyt.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The EurekaApplication class is the entry point of the Spring Boot application.
 * It uses the @SpringBootApplication and @EnableEurekaServer annotations to
 * configure the application as a Eureka server.
 * <p>
 * Eureka server is a service discovery pattern implementation, where every
 * microservice is registered and a client microservice looks up the Eureka
 * server to get a dependent microservice to get the job done.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
