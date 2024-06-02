package tyt.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 * Security configuration class for the application.
 * This class is responsible for setting up the security context and HTTP security configurations.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Creates a SecurityWebFilterChain bean that can be used by the Spring Security framework.
     * This method configures the HTTP security for the application.
     *
     * @param http ServerHttpSecurity object to configure the HTTP security.
     * @return a SecurityWebFilterChain object that holds the security filter chain for the application.
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http

                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}