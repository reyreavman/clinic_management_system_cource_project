package ru.rrk.manager.restClients.appointment.result.state;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.appoinments.result.state.payload.NewAppointmentResultStatePayload;
import ru.rrk.manager.controller.appoinments.result.state.payload.UpdateAppointmentResultStatePayload;
import ru.rrk.manager.entity.appointments.AppointmentResultState;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentResultStateRestClientImpl implements AppointmentResultStateRestClient {
    private static final ParameterizedTypeReference<List<AppointmentResultState>> STATE_TYPE_REFERENCE = new ParameterizedTypeReference<List<AppointmentResultState>>() {
    };
    private final RestClient client;

    @Override
    public List<AppointmentResultState> findAllStates() {
        return this.client
                .get()
                .uri("/clinic-api/appointments/results/states")
                .retrieve()
                .body(STATE_TYPE_REFERENCE);
    }

    @Override
    public AppointmentResultState createState(String state) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/appointments/results/states")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewAppointmentResultStatePayload(state))
                    .retrieve()
                    .body(AppointmentResultState.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
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

    @Override
    public void updateState(int stateId, String state) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/appointments/results/states/{stateId}", stateId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateAppointmentResultStatePayload(state))
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
                    .uri("/clinic-api/appointments/results/states/{stateId}", stateId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
