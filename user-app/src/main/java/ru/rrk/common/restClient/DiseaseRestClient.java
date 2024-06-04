package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.Disease;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DiseaseRestClient {
    private static final ParameterizedTypeReference<List<Disease>> DISEASE_TYPE_REFERENCE = new ParameterizedTypeReference<List<Disease>>() {
    };
    private final RestClient client;

    public List<Disease> findAllDiseases(String filter) {
        return this.client
                .get()
                .uri("/clinic-api/diagnoses/diseases?filter={filter}", filter)
                .retrieve()
                .body(DISEASE_TYPE_REFERENCE);
    }

    public Optional<Disease> findDisease(int diseaseId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/diagnoses/diseases/{diseaseId}", diseaseId)
                    .retrieve()
                    .body(Disease.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
