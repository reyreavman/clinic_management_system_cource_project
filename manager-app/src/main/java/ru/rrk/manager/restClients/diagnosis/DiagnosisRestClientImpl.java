package ru.rrk.manager.restClients.diagnosis;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.diagnosis.payload.NewDiagnosisPayload;
import ru.rrk.manager.controller.diagnosis.payload.UpdateDiagnosisPayload;
import ru.rrk.manager.entity.Diagnosis;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DiagnosisRestClientImpl implements DiagnosisRestClient {
    private final static ParameterizedTypeReference<List<Diagnosis>> DIAGNOSIS_TYPE_REFERENCE = new ParameterizedTypeReference<List<Diagnosis>>() {
    };
    private final RestClient client;

    @Override
    public List<Diagnosis> findAllDiagnosis() {
        this.client
                .get()
                .uri("clinic-api/diagnoses")
                .retrieve()
                .body(DIAGNOSIS_TYPE_REFERENCE);
    }

    @Override
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

    @Override
    public Optional<Diagnosis> findDiagnosis(Integer diagnosisId) {
        try {
            this.client
                    .get()
                    .uri("clinic-api/diagnoses/{diagnosisId}", diagnosisId)
                    .retrieve()
                    .body(Diagnosis.class);
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateDiagnosis(Integer diagnosisId, Integer diseaseId, String description) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/diagnoses/{diagnosisId}", diagnosisId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateDiagnosisPayload(diseaseId, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteDiagnosis(Integer diagnosisId) {
        try {
            this.client
                    .delete()
                    .uri("clinic-api/diagnoses/{diagnosisId}", diagnosisId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
