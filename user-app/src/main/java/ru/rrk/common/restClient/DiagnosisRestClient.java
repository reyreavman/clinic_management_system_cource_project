package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.Diagnosis;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.vet.controller.NewDiagnosisPayload;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DiagnosisRestClient {
    private final static ParameterizedTypeReference<List<Diagnosis>> DIAGNOSIS_TYPE_REFERENCE = new ParameterizedTypeReference<List<Diagnosis>>() {
    };
    private final RestClient client;

    public List<Diagnosis> findAllDiagnosis() {
        return this.client
                .get()
                .uri("clinic-api/diagnoses")
                .retrieve()
                .body(DIAGNOSIS_TYPE_REFERENCE);
    }

    public Diagnosis createDiagnosis(Integer diseaseId, String description) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/diagnoses")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewDiagnosisPayload(diseaseId, description))
                    .retrieve()
                    .body(Diagnosis.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public Optional<Diagnosis> findDiagnosis(Integer diagnosisId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("clinic-api/diagnoses/{diagnosisId}", diagnosisId)
                    .retrieve()
                    .body(Diagnosis.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
