package ru.rrk.common.viewModels.appointment;

import lombok.Builder;

@Builder
public record AppointmentResultSummaryView(
        int id,
        int currentAppointmentId,
        Integer nextAppointmentId,
        int appointmentResultStateId,
        String appointmentResultState,
        Integer diagnosisId,
        String diagnosisDescription,
        Integer diseaseId,
        Integer diseaseCode,
        String diseaseDescription,
        String advice,
        String prescription
) {
}
