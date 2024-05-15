package ru.rrk.clinic.service.appointment.appointment;

import ru.rrk.clinic.entity.appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentService {
    Iterable<Appointment> findAllAppointments();

    Appointment createAppointment(Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId);

    Optional<Appointment> findAppointment(Integer appointmentId);

    void updateAppointment(Integer appointmentId, Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId);

    void deleteAppointment(Integer appointmentId);
}
