package ru.rrk.common.mapper.appointment;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.common.dto.appointment.Appointment;
import ru.rrk.common.viewModels.appointment.AppointmentPrimaryView;

public class AppointmentPrimaryViewConverter implements Converter<Appointment, AppointmentPrimaryView> {
    @Override
    public AppointmentPrimaryView convert(Appointment appointment) {
        return AppointmentPrimaryView.builder()
                .id(appointment.id())
                .petId(appointment.pet().id())
                .petName(appointment.pet().name())
                .vetId(appointment.vet().getId())
                .vetFullName(appointment.vet().getFirstName().concat(" ").concat(appointment.vet().getLastName()))
                .date(appointment.date())
                .time(appointment.time())
                .description(appointment.description())
                .checkup(appointment.checkup())
                .build();
    }
}
