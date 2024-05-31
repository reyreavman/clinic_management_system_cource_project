package ru.rrk.user.receptionist.mapper.appointment;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.Appointment;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentListView;

public class AppointmentListViewConverter implements Converter<Appointment, AppointmentListView> {
    @Override
    public AppointmentListView convert(Appointment appointment) {
        return AppointmentListView.builder()
                .id(appointment.id())
                .petId(appointment.pet().id())
                .petName(appointment.pet().name())
                .vetId(appointment.vet().getId())
                .vetFullName(appointment.vet().getFirstName().concat(" ").concat(appointment.vet().getLastName()))
                .date(appointment.date())
                .time(appointment.time())
                .build();
    }
}
