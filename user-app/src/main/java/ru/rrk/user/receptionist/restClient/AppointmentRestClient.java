package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.dto.Appointment;

import java.util.List;
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
}
