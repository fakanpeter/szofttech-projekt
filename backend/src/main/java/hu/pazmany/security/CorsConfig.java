package hu.pazmany.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "https://vau-vau.web.app/", // from Firebase webapp frontend
                    "http://127.0.0.1:3000/", "http://localhost:3000/", // from nodejs frontend
                    "http://127.0.0.1/", "http://localhost/") // from nginx frontend
                .allowedMethods("*");
    }
}
