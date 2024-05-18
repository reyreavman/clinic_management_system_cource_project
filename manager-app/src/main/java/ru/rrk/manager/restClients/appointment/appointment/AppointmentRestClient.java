package ru.rrk.manager.restClients.appointment.appointment;

import ru.rrk.manager.entity.appointments.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRestClient {
    List<Appointment> findAllAppointments();

    Appointment createAppointment(Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId, Integer receptionistId);

    Optional<Appointment> findAppointment(Integer appointmentId);

    void updateAppointment(Integer appointmentId, Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId, Integer receptionistId);

    void deleteAppointment(Integer appointmentId);

}
