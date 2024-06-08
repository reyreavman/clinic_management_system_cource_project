package ru.rrk.eureka.config;

import jakarta.annotation.Priority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeansConfig {
    @Bean
    @Priority(0)
    public SecurityFilterChain APIsecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/eureka/apps", "/eureka/apps/**")
                .authorizeHttpRequests(customizer -> customizer
                        .anyRequest().hasAuthority("SCOPE_discovery"))
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .oauth2ResourceServer(customizer -> customizer.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    @Priority(1)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(customizer -> customizer
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
