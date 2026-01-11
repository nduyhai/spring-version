package com.nduyhai.version;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ApiVersionInserter;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@SpringBootApplication
public class VersionApplication {
    public static Logger logger = Logger.getLogger(VersionApplication.class.getName());

    static void main(String[] args) {
        SpringApplication.run(VersionApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void captureGreeting() {
        try {
            logger.info("I have just started up");
            RestClient client = RestClient.builder()
                    .baseUrl("http://localhost:8080")
                    .apiVersionInserter(ApiVersionInserter.useHeader("X-API-Version"))
                    .build();

            String res = client.get().uri("/greeting")
                    .apiVersion("2.0.0")
                    .retrieve()
                    .body(String.class);

            logger.info("Receive from greeting: " + res);
        } catch (Exception ex) {
            logger.warning(ex.getMessage());
        }
    }
}


@Configuration
class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer.useRequestHeader("X-API-Version");
    }
}

@RestController
class V1Controller {
    @GetMapping(path = "/greeting", version = "1.0.0")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello from V1");
    }
}

@RestController
class V2Controller {
    @GetMapping(path = "/greeting", version = "2.0.0")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello from V2");
    }
}