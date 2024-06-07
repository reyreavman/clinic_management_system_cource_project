package ru.rrk.users.receptionist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.rrk.common.restClient.*;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.restClient.checkup.CheckupResultRestClient;
import ru.rrk.common.restClient.checkup.CheckupStateRestClient;
import ru.rrk.common.restClient.checkup.CheckupTypeRestClient;
import ru.rrk.common.restClient.pet.*;
import ru.rrk.common.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class RestClientBeansConfig {

    @Bean
    public ReceptionistRestClient receptionistRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new ReceptionistRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new VetRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new AppointmentRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new CheckupRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new PetRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new CheckupTypeRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new CheckupResultRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
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
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new CheckupStateRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public PetTypeRestClient petTypeRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new PetTypeRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public PetBreedRestClient petBreedRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new PetBreedRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public GenderRestClient genderRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new GenderRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public LabelRestClient labelRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new LabelRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public ClientRestClient clientRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new ClientRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public AppointmentResultRestClient appointmentResultRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new AppointmentResultRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public DiseaseRestClient diseaseRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new DiseaseRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public AppointmentResultStateRestClient appointmentResultStateRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new AppointmentResultStateRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }

    @Bean
    public DiagnosisRestClient diagnosisRestClient(
            @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
            LoadBalancerClient loadBalancerClient) {
        return new DiagnosisRestClient(RestClient.builder()
                .baseUrl(clinicBaseUri)
                .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(
                                        clientRegistrationRepository, authorizedClientRepository), registrationId))
                .build());
    }
}

//
//    @Configuration
//    @ConditionalOnProperty(name = "eureka.client.enabled", havingValue = "true", matchIfMissing = true)
//    public static class CloudClientConfig {
//        @Bean
//        public ReceptionistRestClient receptionistRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new ReceptionistRestClient(RestClient.builder()
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
//        public VetRestClient vetRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new VetRestClient(RestClient.builder()
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
//        public AppointmentRestClient appointmentRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new AppointmentRestClient(RestClient.builder()
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
//        public CheckupRestClient checkupRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupRestClient(RestClient.builder()
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
//        public PetRestClient petRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new PetRestClient(RestClient.builder()
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
//        public CheckupTypeRestClient typeRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupTypeRestClient(RestClient.builder()
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
//        public CheckupResultRestClient resultRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupResultRestClient(RestClient.builder()
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
//        public CheckupStateRestClient stateRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new CheckupStateRestClient(RestClient.builder()
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
//        public PetTypeRestClient petTypeRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new PetTypeRestClient(RestClient.builder()
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
//        public PetBreedRestClient petBreedRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new PetBreedRestClient(RestClient.builder()
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
//        public GenderRestClient genderRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new GenderRestClient(RestClient.builder()
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
//        public LabelRestClient labelRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new LabelRestClient(RestClient.builder()
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
//        public ClientRestClient clientRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new ClientRestClient(RestClient.builder()
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
//        public AppointmentResultRestClient appointmentResultRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new AppointmentResultRestClient(RestClient.builder()
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
//        public DiseaseRestClient diseaseRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new DiseaseRestClient(RestClient.builder()
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
//        public AppointmentResultStateRestClient appointmentResultStateRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new AppointmentResultStateRestClient(RestClient.builder()
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
//        public DiagnosisRestClient diagnosisRestClient(
//                @Value("${kupang.services.clinic.uri:http://127.0.0.1:8081}") String clinicBaseUri,
//                ClientRegistrationRepository clientRegistrationRepository,
//                OAuth2AuthorizedClientRepository authorizedClientRepository,
//                @Value("${kupang.services.clinic.registration-id:keycloak}") String registrationId,
//                LoadBalancerClient loadBalancerClient) {
//            return new DiagnosisRestClient(RestClient.builder()
//                    .baseUrl(clinicBaseUri)
//                    .requestInterceptor(new LoadBalancerInterceptor(loadBalancerClient))
//                    .requestInterceptor(
//                            new OAuthClientHttpRequestInterceptor(
//                                    new DefaultOAuth2AuthorizedClientManager(
//                                            clientRegistrationRepository, authorizedClientRepository), registrationId))
//                    .build());
//        }
//    }