package ru.rrk.manager.restClients.checkup.result;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.checkup.result.payload.NewCheckupResultPayload;
import ru.rrk.manager.controller.checkup.result.payload.UpdateCheckupResultPayload;
import ru.rrk.manager.entity.checkup.CheckupResult;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupResultRestClientImpl implements CheckupResultRestClient {
    private static final ParameterizedTypeReference<List<CheckupResult>> RESULT_TYPE_REFERENCE = new ParameterizedTypeReference<List<CheckupResult>>() {
    };
    private final RestClient client;

    @Override
    public List<CheckupResult> findAllResults() {
        return this.client
                .get()
                .uri("/clinic-api/checkups/results")
                .retrieve()
                .body(RESULT_TYPE_REFERENCE);
    }

    @Override
    public CheckupResult createResult(String description) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/checkups/results")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewCheckupResultPayload(description))
                    .retrieve()
                    .body(CheckupResult.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<CheckupResult> findResult(int resultId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/checkups/results/{resultId}", resultId)
                    .retrieve()
                    .body(CheckupResult.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateResult(int resultId, String description) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/checkups/results/{resultId}", resultId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateCheckupResultPayload(description))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteResult(int resultId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/checkups/results/{resultId}", resultId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
