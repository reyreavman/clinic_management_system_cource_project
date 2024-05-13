package ru.rrk.manager.restClients.checkup.state;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.checkup.state.payload.NewCheckupStatePayload;
import ru.rrk.manager.controller.checkup.state.payload.UpdateCheckupStatePayload;
import ru.rrk.manager.entity.checkup.CheckupState;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupStateRestClientImpl implements CheckupStateRestClient {
    private static final ParameterizedTypeReference<List<CheckupState>> STATE_TYPE_REFERENCE = new ParameterizedTypeReference<List<CheckupState>>() {
    };
    private final RestClient client;

    @Override
    public List<CheckupState> findAllStates() {
        return this.client
                .get()
                .uri("/clinic-api/checkups/states")
                .retrieve()
                .body(STATE_TYPE_REFERENCE);
    }

    @Override
    public CheckupState createState(String state) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/checkups/states")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewCheckupStatePayload(state))
                    .retrieve()
                    .body(CheckupState.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<CheckupState> findState(int stateId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/checkups/states/{stateId}", stateId)
                    .retrieve()
                    .body(CheckupState.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateState(int stateId, String state) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/checkups/states/{stateId}", stateId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateCheckupStatePayload(state))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteState(int stateId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/checkups/states/{stateId}", stateId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
