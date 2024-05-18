package ru.rrk.manager.restClients.appointment.result.result;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.appoinments.result.result.payload.NewAppointmentResultPayload;
import ru.rrk.manager.controller.appoinments.result.result.payload.UpdateAppointmentResultPayload;
import ru.rrk.manager.entity.appointments.AppointmentResult;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentResultRestClientImpl implements AppointmentResultRestClient {
    private static final ParameterizedTypeReference<List<AppointmentResult>> APPOINTMENT_RESULT_TYPE_REFERENCE = new ParameterizedTypeReference<List<AppointmentResult>>() {
    };
    private final RestClient client;

    @Override
    public List<AppointmentResult> findAllAppointmentResults() {
        return this.client
                .get()
                .uri("clinic-api/appointments/results")
                .retrieve()
                .body(APPOINTMENT_RESULT_TYPE_REFERENCE);
    }

    @Override
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


    @Override
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

    @Override
    public void updateAppointmentResult(Integer appointmentResultId, Integer currentAppointment, Integer nextAppointment, Integer state, Integer diagnosis, String advice, String prescription) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/appointments/results/{resultId}", appointmentResultId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateAppointmentResultPayload(currentAppointment, nextAppointment, state, diagnosis, advice, prescription))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }


    @Override
    public void deleteAppointment(Integer appointmentResultId) {
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
