package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.appointment.AppointmentResult;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.vet.controller.NewAppointmentResultPayload;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentResultRestClient {
    private static final ParameterizedTypeReference<List<AppointmentResult>> APPOINTMENT_RESULT_TYPE_REFERENCE = new ParameterizedTypeReference<List<AppointmentResult>>() {
    };
    private final RestClient client;

    public List<AppointmentResult> findAllAppointmentResults() {
        return this.client
                .get()
                .uri("clinic-api/appointments/results")
                .retrieve()
                .body(APPOINTMENT_RESULT_TYPE_REFERENCE);
    }

    public AppointmentResult createAppointmentResult(Integer currentAppointmentId, Integer nextAppointmentId, Integer stateId, Integer diagnosisId, String advice, String prescription) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/appointments/results")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewAppointmentResultPayload(currentAppointmentId, nextAppointmentId, stateId, diagnosisId, advice, prescription))
                    .retrieve()
                    .body(AppointmentResult.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public Optional<AppointmentResult> findAppointmentResult(Integer appointmentResultId) {
        try {
            return Optional.ofNullable(
                    this.client
                            .get()
                            .uri("clinic-api/appointments/results/{resultId}", appointmentResultId)
                            .retrieve()
                            .body(AppointmentResult.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public void deleteAppointmentResult(Integer appointmentResultId) {
        try {
            this.client
                    .delete()
                    .uri("clinic-api/appointments/results/{resultId}", appointmentResultId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
