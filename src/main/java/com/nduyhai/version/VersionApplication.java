package com.nduyhai.version;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class VersionApplication {

    static void main(String[] args) {
        SpringApplication.run(VersionApplication.class, args);
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
class V0Controller {
    @GetMapping(path = "/greeting", version = "1.0.0")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello from V0");
    }
}

@RestController
class V1Controller {
    @GetMapping(path = "/greeting", version = "2.0.0")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok("Hello from V1");
    }
}