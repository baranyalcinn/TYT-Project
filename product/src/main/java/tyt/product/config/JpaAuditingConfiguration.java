package tyt.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JpaAuditingConfiguration is a configuration class that enables JPA Auditing.
 * <p>
 * JPA Auditing helps us to persist some audit logs automatically. For example,
 * who created or changed an entity and when it happened.
 *
 * @Configuration - This annotation class indicates that the class can be used
 * by the Spring IoC container as a source of bean definitions.
 *
 * @EnableJpaAuditing - This annotation is to activate auditing via annotation
 * configuration
 *
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}