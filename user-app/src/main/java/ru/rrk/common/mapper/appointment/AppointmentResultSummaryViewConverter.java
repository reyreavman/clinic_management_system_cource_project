package ru.rrk.common.mapper.appointment;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.common.dto.appointment.AppointmentResult;
import ru.rrk.common.viewModels.appointment.AppointmentResultSummaryView;

public class AppointmentResultSummaryViewConverter implements Converter<AppointmentResult, AppointmentResultSummaryView> {
    @Override
    public AppointmentResultSummaryView convert(AppointmentResult appointmentResult) {
        return AppointmentResultSummaryView.builder()
                .id(appointmentResult.id())
                .currentAppointmentId(appointmentResult.currentAppointment().id())
                .nextAppointmentId(appointmentResult.nextAppointment() != null ? appointmentResult.nextAppointment().id(): null)
                .appointmentResultStateId(appointmentResult.state().id())
                .appointmentResultState(appointmentResult.state().state())
                .diagnosisId(appointmentResult.diagnosis() != null ? appointmentResult.diagnosis().id() : null)
                .diagnosisDescription(appointmentResult.diagnosis() != null ? appointmentResult.diagnosis().description() : null)
                .diseaseId(appointmentResult.diagnosis() != null ? appointmentResult.diagnosis().disease().id() : null)
                .diseaseCode(appointmentResult.diagnosis() != null ? appointmentResult.diagnosis().disease().code() : null)
                .diseaseDescription(appointmentResult.diagnosis() != null ? appointmentResult.diagnosis().disease().description() : null)
                .advice(appointmentResult.advice() != null ? appointmentResult.advice() : null)
                .prescription(appointmentResult.prescription() != null ? appointmentResult.prescription() : null)
                .build();
    }
}
