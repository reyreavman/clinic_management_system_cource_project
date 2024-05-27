package ru.rrk.user.receptionist.converter;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.AppointmentDTO;
import ru.rrk.user.receptionist.entity.Appointment;

public class AppointmentDTOConverter implements Converter<Appointment, AppointmentDTO> {
    @Override
    public AppointmentDTO convert(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.id())
                .petId(appointment.pet().id())
                .petName(appointment.pet().name())
                .vetId(appointment.vet().getId())
                .vetFullName(appointment.vet().getFirstName().concat(" ").concat(appointment.vet().getLastName()))
                .date(appointment.date())
                .time(appointment.time())
                .description(appointment.description())
                .checkup(appointment.checkup() == null ? null : appointment.checkup())
                .build();
    }
}
