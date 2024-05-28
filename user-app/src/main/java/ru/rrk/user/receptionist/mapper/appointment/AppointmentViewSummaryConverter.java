package ru.rrk.user.receptionist.mapper.appointment;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.Appointment;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentViewSummary;

public class AppointmentViewSummaryConverter implements Converter<Appointment, AppointmentViewSummary> {
    @Override
    public AppointmentViewSummary convert(Appointment appointment) {
        return AppointmentViewSummary.builder()
                .id(appointment.id())
                .petId(appointment.pet().id())
                .petName(appointment.pet().name())
                .vetId(appointment.vet().getId())
                .vetFullName(appointment.vet().getFirstName().concat(" ").concat(appointment.vet().getLastName()))
                .time(appointment.time())
                .description(appointment.description())
                .checkup(appointment.checkup() == null ? null : appointment.checkup())
                .build();
    }
}
