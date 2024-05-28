package ru.rrk.user.receptionist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.restClient.*;
import ru.rrk.user.receptionist.restClient.checkup.CheckupRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupResultRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupStateRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupTypeRestClient;
import ru.rrk.user.receptionist.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class RestClientBeansConfig {
    @Bean
    public ReceptionistRestClient receptionistRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new ReceptionistRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public VetRestClient vetRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new VetRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public AppointmentRestClient appointmentRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new AppointmentRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public CheckupRestClient checkupRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new CheckupRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public PetRestClient petRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new PetRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public CheckupTypeRestClient typeRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new CheckupTypeRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }


    @Bean
    public CheckupResultRestClient resultRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new CheckupResultRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public CheckupStateRestClient stateRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new CheckupStateRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }
}