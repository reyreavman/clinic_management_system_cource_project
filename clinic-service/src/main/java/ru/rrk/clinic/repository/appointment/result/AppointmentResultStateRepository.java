package ru.rrk.clinic.repository.appointment.result;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.appointment.AppointmentsResultState;

public interface AppointmentResultStateRepository extends CrudRepository<AppointmentsResultState, Integer> {
}
