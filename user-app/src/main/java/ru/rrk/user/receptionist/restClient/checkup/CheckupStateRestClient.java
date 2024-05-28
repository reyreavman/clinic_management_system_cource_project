package ru.rrk.user.receptionist.restClient.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.dto.checkup.CheckupState;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupStateRestClient {
    private static final ParameterizedTypeReference<List<CheckupState>> STATE_TYPE_REFERENCE = new ParameterizedTypeReference<List<CheckupState>>() {
    };
    private final RestClient restClient;

    public Optional<CheckupState> findState(int stateId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/clinic-api/checkups/states/{stateId}", stateId)
                    .retrieve()
                    .body(CheckupState.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public List<CheckupState> findAllStates() {
        return this.restClient
                .get()
                .uri("/clinic-api/checkups/states")
                .retrieve()
                .body(STATE_TYPE_REFERENCE);
    }
}
