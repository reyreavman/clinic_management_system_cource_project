package ru.rrk.manager.restClients.disease;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.clients.payload.UpdateClientPayload;
import ru.rrk.manager.entity.Disease;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DiseaseRestClientImpl implements DiseaseRestClient {
    private static final ParameterizedTypeReference<List<Disease>> DISEASE_TYPE_REFERENCE = new ParameterizedTypeReference<List<Disease>>() {
    };
    private final RestClient client;

    @Override
    public List<Disease> findAllDiseases(String filter) {
        return this.client
                .get()
                .uri("/clinic-api/diseases?filter={filter}", filter)
                .retrieve()
                .body(DISEASE_TYPE_REFERENCE);
    }

    @Override
    public Disease createDisease(int code, int description) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/diseases")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewDiseasePayload(code, description))
                    .retrieve()
                    .body(Disease.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Disease> findDisease(int diseaseId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/diseases/{diseaseId}", diseaseId)
                    .retrieve()
                    .body(Disease.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateDisease(int diseaseId, int code, String description) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/diseases/{diseaseId}", diseaseId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateDiseasePayload(code, description))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteDisease(int diseaseId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/diseases/{diseaseId}", diseaseId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
