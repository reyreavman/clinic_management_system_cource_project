package ru.rrk.manager.restClients.appointment.appointment;

import ru.rrk.manager.entity.appointments.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRestClient {
    List<Appointment> findAllAppointments();

    Appointment createAppointment(Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId);

    Optional<Appointment> findAppointment(int appointmentId);

    void updateAppointment(int appointmentId, Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId);

    void deleteAppointment(int appointmentId);

}
