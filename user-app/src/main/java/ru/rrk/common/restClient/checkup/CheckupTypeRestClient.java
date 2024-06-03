package ru.rrk.common.restClient.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.checkup.CheckupType;

import java.util.List;

@RequiredArgsConstructor
public class CheckupTypeRestClient {
    private static final ParameterizedTypeReference<List<CheckupType>> CHECKUPTYPE_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient restClient;

    public List<CheckupType> findAllTypes() {
        return this.restClient
                .get()
                .uri("/clinic-api/checkups/types")
                .retrieve()
                .body(CHECKUPTYPE_TYPE_REFERENCE);
    }
}
