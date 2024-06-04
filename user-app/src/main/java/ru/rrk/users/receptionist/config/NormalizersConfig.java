package ru.rrk.users.receptionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rrk.common.mapper.checkup.CheckupPayloadNormalizer;
import ru.rrk.common.restClient.checkup.CheckupResultRestClient;
import ru.rrk.common.restClient.checkup.CheckupStateRestClient;

@Configuration
public class NormalizersConfig {
    @Bean
    public CheckupPayloadNormalizer checkupPayloadNormalizer(CheckupStateRestClient stateRestClient, CheckupResultRestClient resultRestClient) {
        return new CheckupPayloadNormalizer(stateRestClient, resultRestClient);
    }
}
