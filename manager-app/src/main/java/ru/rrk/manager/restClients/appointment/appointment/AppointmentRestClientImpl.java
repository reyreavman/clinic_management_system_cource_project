package ru.rrk.manager.restClients.appointment.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.appoinments.appointment.payload.NewAppointmentPayload;
import ru.rrk.manager.controller.appoinments.appointment.payload.UpdateAppointmentPayload;
import ru.rrk.manager.entity.appointments.Appointment;
import ru.rrk.manager.restClients.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class AppointmentRestClientImpl implements AppointmentRestClient {
    private static final ParameterizedTypeReference<List<Appointment>> APPOINTMENT_TYPE_REFERENCE = new ParameterizedTypeReference<List<Appointment>>() {
    };
    private final RestClient client;

    @Override
    public List<Appointment> findAllAppointments() {
        return this.client
                .get()
                .uri("clinic-api/appointments")
                .retrieve()
                .body(APPOINTMENT_TYPE_REFERENCE);
    }

    @Override
    public Appointment createAppointment(Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/appointments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewAppointmentPayload(petId, vetId, date, time, description, checkupId))
                    .retrieve()
                    .body(Appointment.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Appointment> findAppointment(int appointmentId) {
        try {
            return Optional.ofNullable(
                    this.client
                            .get()
                            .uri("clinic-api/appointments/{appointmentId}", appointmentId)
                            .retrieve()
                            .body(Appointment.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateAppointment(int appointmentId, Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/appointments/{appointmentId}", appointmentId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateAppointmentPayload(petId, vetId, date, time, description, checkupId))
                    .retrieve()
                    .toBodilessEntity();
        }  catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        try {
            this.client
                    .delete()
                    .uri("clinic-api/appointments/{appointmentId}", appointmentId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
