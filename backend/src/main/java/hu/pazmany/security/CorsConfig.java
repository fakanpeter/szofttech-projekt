package hu.pazmany.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost/", "https://localhost/", "http://127.0.0.1/", "https://127.0.0.1/", "https://vau-vau.web.app/", "http://localhost:3000", "http://localhost:3001", "http://localhost:3002", "http://localhost:8080")
                .allowedMethods("*");
    }
}