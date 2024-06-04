package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.receptionist.controller.appointment.payload.NewAppointmentDetailsPayload;
import ru.rrk.common.dto.appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentRestClient {
    private static final ParameterizedTypeReference<List<Appointment>> APPOINTMENT_TYPE_REFERENCE = new ParameterizedTypeReference<List<Appointment>>() {
    };
    private final RestClient restClient;

    public List<Appointment> findAllAppointments() {
        return this.restClient
                .get()
                .uri("clinic-api/appointments")
                .retrieve()
                .body(APPOINTMENT_TYPE_REFERENCE);
    }

    public Optional<Appointment> findAppointment(Integer appointmentId) {
        try {
            return Optional.ofNullable(
                    this.restClient
                            .get()
                            .uri("clinic-api/appointments/{appointmentId}", appointmentId)
                            .retrieve()
                            .body(Appointment.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public void deleteAppointment(Integer appointmentId) {
        try {
            this.restClient
                    .delete()
                    .uri("clinic-api/appointments/{appointmentId}", appointmentId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }

    public Appointment createAppointment(Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId, Integer receptionistId) {
        try {
            return this.restClient
                    .post()
                    .uri("clinic-api/appointments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewAppointmentDetailsPayload(petId, vetId, date, time, description, checkupId, receptionistId))
                    .retrieve()
                    .body(Appointment.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
