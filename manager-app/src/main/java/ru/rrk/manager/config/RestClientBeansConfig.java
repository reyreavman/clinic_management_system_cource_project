package ru.rrk.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.restClients.appointment.appointment.AppointmentRestClientImpl;
import ru.rrk.manager.restClients.appointment.result.result.AppointmentResultRestClientImpl;
import ru.rrk.manager.restClients.appointment.result.state.AppointmentResultStateRestClientImpl;
import ru.rrk.manager.restClients.checkup.checkup.CheckupRestClientImpl;
import ru.rrk.manager.restClients.checkup.result.CheckupResultRestClientImpl;
import ru.rrk.manager.restClients.checkup.state.CheckupStateRestClientImpl;
import ru.rrk.manager.restClients.checkup.type.CheckupTypeRestClientImpl;
import ru.rrk.manager.restClients.client.ClientRestClientImpl;
import ru.rrk.manager.restClients.diagnosis.DiagnosisRestClientImpl;
import ru.rrk.manager.restClients.disease.DiseaseRestClientImpl;
import ru.rrk.manager.restClients.gender.GenderRestClientImpl;
import ru.rrk.manager.restClients.pet.breed.PetBreedRestClientImpl;
import ru.rrk.manager.restClients.pet.label.LabelRestClientImpl;
import ru.rrk.manager.restClients.pet.pet.PetRestClientImpl;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClientImpl;
import ru.rrk.manager.restClients.receptionist.ReceptionistRestClientImpl;
import ru.rrk.manager.restClients.vet.speciality.SpecialityRestClientImpl;
import ru.rrk.manager.restClients.vet.vet.VetRestClientImpl;
import ru.rrk.manager.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class RestClientBeansConfig {
    public static class StandaloneClientConfig {
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

        @Bean
        public PetRestClientImpl petRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new PetRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }

        @Bean
        public CheckupStateRestClientImpl checkupStateRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new CheckupStateRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }

        @Bean
        public CheckupResultRestClientImpl checkupResultRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new CheckupResultRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }

        @Bean
        public AppointmentResultStateRestClientImpl AppointmentResultStateRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new AppointmentResultStateRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }

        @Bean
        public CheckupRestClientImpl checkupRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new CheckupRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }

        @Bean
        public AppointmentRestClientImpl appointmentRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new AppointmentRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }


        @Bean
        public DiagnosisRestClientImpl diagnosisRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new DiagnosisRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }

        @Bean
        public AppointmentResultRestClientImpl appointmentResultRestClient(
                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
                ClientRegistrationRepository clientRegistrationRepository,
                OAuth2AuthorizedClientRepository authorizedClientRepository,
                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId) {
            return new AppointmentResultRestClientImpl(RestClient.builder()
                    .baseUrl(clinicBaseUri)
                    .requestInterceptor(
                            new OAuthClientHttpRequestInterceptor(
                                    new DefaultOAuth2AuthorizedClientManager(
                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
                    .build());
        }
    }
//
//
//
//    @Configuration
//    @ConditionalOnProperty(name = "eureka.client.enabled", havingValue = "true", matchIfMissing = true)
//    public static class CloudClientConfig {
//        @Bean
//        public ClientRestClientImpl clientsRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new ClientRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public SpecialityRestClientImpl specialityRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new SpecialityRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public VetRestClientImpl vetRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new VetRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public GenderRestClientImpl genderRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new GenderRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public LabelRestClientImpl labelRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new LabelRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public DiseaseRestClientImpl diseaseRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new DiseaseRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public ReceptionistRestClientImpl receptionistRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new ReceptionistRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public CheckupTypeRestClientImpl checkupTypeRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupTypeRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public PetTypeRestClientImpl petTypeRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new PetTypeRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public PetBreedRestClientImpl petBreedRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new PetBreedRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public PetRestClientImpl petRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new PetRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public CheckupStateRestClientImpl checkupStateRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupStateRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public CheckupResultRestClientImpl checkupResultRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupResultRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public AppointmentResultStateRestClientImpl AppointmentResultStateRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new AppointmentResultStateRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public CheckupRestClientImpl checkupRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public AppointmentRestClientImpl appointmentRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new AppointmentRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//
//        @Bean
//        public DiagnosisRestClientImpl diagnosisRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new DiagnosisRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//
//        @Bean
//        public AppointmentResultRestClientImpl appointmentResultRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new AppointmentResultRestClientImpl(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//    }
}