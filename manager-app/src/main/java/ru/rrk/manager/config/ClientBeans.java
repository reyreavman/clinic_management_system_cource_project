package ru.rrk.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.client.client.ClientRestClientImpl;
import ru.rrk.manager.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class ClientBeans {
    @Bean
    public ClientRestClientImpl clientsRestClient(
            @Value("${kupang.services.clinic.uri:http://localhost:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new ClientRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }
}
