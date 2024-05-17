package ru.rrk.clinic.repository.appointment.result;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.appointment.AppointmentResult;

public interface AppointmentResultRepository extends CrudRepository<AppointmentResult, Integer> {
}
