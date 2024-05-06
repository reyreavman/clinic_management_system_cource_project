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
                        .requestMatchers(HttpMethod.POST, "/clinic-api/clients").hasAuthority("SCOPE_edit_clinic_users")
                        .requestMatchers(HttpMethod.PATCH, "/clinic-api/clients/{clientId:\\+d}").hasAuthority("SCOPE_edit_clinic_users")
                        .requestMatchers(HttpMethod.DELETE, "/clinic-api/clients/{clientId:\\+d}").hasAuthority("SCOPE_edit_clinic_users")
                        .requestMatchers(HttpMethod.GET, "/clinic-api/clients").hasAuthority("SCOPE_view_clinic_users")

                        .requestMatchers(HttpMethod.POST, "/clinic-api/specialities").hasAuthority("SCOPE_edit_clinic_specialities")
                        .requestMatchers(HttpMethod.PATCH, "/clinic-api/specialities/{specialityId:\\d+}").hasAuthority("SCOPE_edit_clinic_specialities")
                        .requestMatchers(HttpMethod.DELETE, "/clinic-api/specialities/{specialityId:\\d+}").hasAuthority("SCOPE_edit_clinic_specialities")
                        .requestMatchers(HttpMethod.GET, "/clinic-api/specialities").hasAuthority("SCOPE_view_clinic_specialities")

                        .requestMatchers(HttpMethod.POST, "/clinic-api/vets").hasAuthority("SCOPE_edit_clinic_vets")
                        .requestMatchers(HttpMethod.PATCH, "/clinic-api/vets/{vetId:\\d+}").hasAuthority("SCOPE_edit_clinic_vets")
                        .requestMatchers(HttpMethod.DELETE, "/clinic-api/vets/{vetId:\\d+}").hasAuthority("SCOPE_edit_clinic_vets")
                        .requestMatchers(HttpMethod.GET, "/clinic-api/vets").hasAuthority("SCOPE_view_clinic_vets")

                        .requestMatchers(HttpMethod.POST, "/clinic-api/genders").hasAuthority("SCOPE_edit_clinic_genders")
                        .requestMatchers(HttpMethod.PATCH, "/clinic-api/genders/{genderId:\\d+}").hasAuthority("SCOPE_edit_clinic_genders")
                        .requestMatchers(HttpMethod.DELETE, "/clinic-api/genders/{genderId:\\d+}").hasAuthority("SCOPE_edit_clinic_genders")
                        .requestMatchers(HttpMethod.GET, "/clinic-api/genders").hasAuthority("SCOPE_view_clinic_genders")

                        .anyRequest().permitAll())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()))
                .build();
    }
}
