package ru.rrk.clinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/clinic-api/clients")
                        .hasAuthority("SCOPE_edit_clinic_users")
                        .requestMatchers(HttpMethod.PATCH, "/clinic-api/clients/{clientId:\\d}")
                        .hasAuthority("SCOPE_edit_clinic_users")
                        .requestMatchers(HttpMethod.DELETE, "/clinic-api/clients/{clientId:\\d}")
                        .hasAuthority("SCOPE_edit_clinic_users")
                        .requestMatchers(HttpMethod.GET)
                        .hasAuthority("SCOPE_view_clinic_users")
                        .anyRequest().denyAll())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}
