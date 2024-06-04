package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.appointment.AppointmentResultState;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentResultStateRestClient {
    private static final ParameterizedTypeReference<List<AppointmentResultState>> STATE_TYPE_REFERENCE = new ParameterizedTypeReference<List<AppointmentResultState>>() {
    };
    private final RestClient client;

    public List<AppointmentResultState> findAllStates() {
        return this.client
                .get()
                .uri("/clinic-api/appointments/results/states")
                .retrieve()
                .body(STATE_TYPE_REFERENCE);
    }

    public Optional<AppointmentResultState> findState(int stateId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/appointments/results/states/{stateId}", stateId)
                    .retrieve()
                    .body(AppointmentResultState.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
