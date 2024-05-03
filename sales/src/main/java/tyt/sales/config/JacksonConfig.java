package tyt.sales.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Configuration class for Jackson, a JSON processing library.
 * This class configures the ObjectMapper to handle LocalDateTime objects.
 */
@Configuration
public class JacksonConfig {

    /**
     * Bean definition for the ObjectMapper.
     * The ObjectMapper is configured to serialize LocalDateTime objects to a specific format.
     *
     * @return ObjectMapper with custom configuration
     */
    @Bean
    public ObjectMapper objectMapper() {
        // Create a new ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a new JavaTimeModule to handle Java 8 date/time types
        JavaTimeModule module = new JavaTimeModule();

        // Add a serializer to the module for LocalDateTime objects
        // The serializer uses a custom format
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        // Register the module with the ObjectMapper
        mapper.registerModule(module);

        // Return the configured ObjectMapper
        return mapper;
    }
}