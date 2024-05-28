package ru.rrk.user.receptionist.restClient.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.controller.BadRequestException;
import ru.rrk.user.receptionist.controller.payload.NewCheckupResultPayload;
import ru.rrk.user.receptionist.dto.checkup.CheckupResult;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupResultRestClient {
    private static final ParameterizedTypeReference<List<CheckupResult>> RESULT_TYPE_REFERENCE = new ParameterizedTypeReference<List<CheckupResult>>() {
    };
    private final RestClient restClient;

    public List<CheckupResult> findAllResults() {
        return this.restClient
                .get()
                .uri("/clinic-api/checkups/results")
                .retrieve()
                .body(RESULT_TYPE_REFERENCE);
    }

    public CheckupResult createResult(String description) {
        try {
            return this.restClient
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

    public Optional<CheckupResult> findResult(int resultId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/clinic-api/checkups/results/{resultId}", resultId)
                    .retrieve()
                    .body(CheckupResult.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
