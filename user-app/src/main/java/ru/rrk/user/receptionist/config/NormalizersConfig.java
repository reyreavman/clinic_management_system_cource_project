package ru.rrk.user.receptionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPayloadNormalizer;
import ru.rrk.user.receptionist.restClient.checkup.CheckupResultRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupStateRestClient;

@Configuration
public class NormalizersConfig {
    @Bean
    public CheckupPayloadNormalizer checkupPayloadNormalizer(CheckupStateRestClient stateRestClient, CheckupResultRestClient resultRestClient) {
        return new CheckupPayloadNormalizer(stateRestClient, resultRestClient);
    }
}
