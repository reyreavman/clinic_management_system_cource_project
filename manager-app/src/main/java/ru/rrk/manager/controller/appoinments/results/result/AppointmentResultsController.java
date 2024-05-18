package ru.rrk.manager.controller.appoinments.results.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.appoinments.results.result.payload.NewAppointmentResultPayload;
import ru.rrk.manager.entity.appointments.AppointmentResult;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.appointment.appointment.AppointmentRestClient;
import ru.rrk.manager.restClients.appointment.result.result.AppointmentResultRestClient;
import ru.rrk.manager.restClients.appointment.result.state.AppointmentResultStateRestClient;
import ru.rrk.manager.restClients.diagnosis.DiagnosisRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments/results")
public class AppointmentResultsController {
    private final AppointmentResultRestClient appointmentResultRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final AppointmentResultStateRestClient appointmentResultStateRestClient;
    private final DiagnosisRestClient diagnosisRestClient;

    @GetMapping("list")
    public String getAppointmentsResultsList(Model model) {
        model.addAttribute("results", this.appointmentResultRestClient.findAllAppointmentResults());
        return "clinic/appointments/results/list";
    }

    @GetMapping("create")
    public String getNewAppointmentResultPage(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments());
        model.addAttribute("states", this.appointmentResultStateRestClient.findAllStates());
        model.addAttribute("diagnoses", this.diagnosisRestClient.findAllDiagnosis());
        return "clinic/appointments/results/new_result";
    }

    @PostMapping("create")
    public String createAppointmentResult(NewAppointmentResultPayload payload, Model model) {
        try {
            AppointmentResult appointmentResult = this.appointmentResultRestClient.createAppointmentResult(payload.currentAppointmentId(), payload.nextAppointmentId(), payload.stateId(), payload.diagnosisId(), payload.advice(), payload.prescription());
            return "redirect:/clinic/appointments/results/%d".formatted(appointmentResult.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/appointments/results/new_result";
        }
    }
}
