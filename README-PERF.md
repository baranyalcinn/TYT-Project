# Performance optimization summary

- Removed unused heavy dependencies from services: guava, xstream, woodstox, httpclient5, servlet-api.
- Switched logging from Log4j2 annotations to SLF4J (`@Slf4j`) and relied on Spring Boot default Logback binding.
- Gateway: kept Spring Cloud Gateway; removed extra WebFlux starter.
- Sales: use framework `spring-webflux` instead of the starter to avoid redundant transitive deps; tuned WebClient with gzip and timeouts; added bounded async dispatch on record notification.
- Enabled lazy initialization for all services and disabled JMX to reduce startup time and memory.
- Enabled HTTP/2 for gateway and set reactive session timeout.

Measure:
- Build sizes: `du -h */target/*.jar`
- Startup: `time java -jar target/app.jar` and check health endpoint.
- Memory: `-XX:NativeMemoryTracking=summary` or container metrics.