package ru.rrk.clinic.repository.appointment.appointment;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.appointment.Appointment;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}
