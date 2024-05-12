package ru.rrk.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.restClients.checkup.type.CheckupTypeRestClientImpl;
import ru.rrk.manager.restClients.client.ClientRestClientImpl;
import ru.rrk.manager.restClients.disease.DiseaseRestClientImpl;
import ru.rrk.manager.restClients.gender.GenderRestClientImpl;
import ru.rrk.manager.restClients.pet.breed.PetBreedRestClientImpl;
import ru.rrk.manager.restClients.pet.label.LabelRestClientImpl;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClientImpl;
import ru.rrk.manager.restClients.receptionist.ReceptionistRestClientImpl;
import ru.rrk.manager.restClients.vet.speciality.SpecialityRestClientImpl;
import ru.rrk.manager.restClients.vet.vet.VetRestClientImpl;
import ru.rrk.manager.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class BeansConfig {
    @Bean
    public ClientRestClientImpl clientsRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
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

    @Bean
    public SpecialityRestClientImpl specialityRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new SpecialityRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public VetRestClientImpl vetRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new VetRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public GenderRestClientImpl genderRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new GenderRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public LabelRestClientImpl labelRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new LabelRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public DiseaseRestClientImpl diseaseRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new DiseaseRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public ReceptionistRestClientImpl receptionistRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new ReceptionistRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public CheckupTypeRestClientImpl checkupTypeRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new CheckupTypeRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public PetTypeRestClientImpl petTypeRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new PetTypeRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public PetBreedRestClientImpl petBreedRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
        return new PetBreedRestClientImpl(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }
}