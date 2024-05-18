package ru.rrk.manager.controller.appoinments.results.result;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.appoinments.results.result.payload.UpdateAppointmentResultPayload;
import ru.rrk.manager.entity.appointments.AppointmentResult;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.appointment.appointment.AppointmentRestClient;
import ru.rrk.manager.restClients.appointment.result.result.AppointmentResultRestClient;
import ru.rrk.manager.restClients.appointment.result.state.AppointmentResultStateRestClient;
import ru.rrk.manager.restClients.diagnosis.DiagnosisRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments/results/{resultId:\\d+}")
public class AppointmentResultController {
    private final AppointmentResultRestClient resultRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final AppointmentResultStateRestClient stateRestClient;
    private final DiagnosisRestClient diagnosisRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("appointmentResult")
    public AppointmentResult appointmentResult(@PathVariable("resultId") int resultId) {
        return this.resultRestClient.findAppointmentResult(resultId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.appointment.result.not_found"));
    }

    @GetMapping
    public String getAppointmentResult() {
        return "clinic/appointments/results/result";
    }

    @GetMapping("edit")
    public String getAppointmentResultEditPage(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments());
        model.addAttribute("states", this.stateRestClient.findAllStates());
        model.addAttribute("diagnoses", this.diagnosisRestClient.findAllDiagnosis());
        return "clinic/appointments/results/edit";
    }

    @PostMapping("edit")
    public String updateAppointmentResult(@ModelAttribute(name = "appointmentResult", binding = false) AppointmentResult appointmentResult,
                                          UpdateAppointmentResultPayload payload, Model model) {
        try {
            this.resultRestClient.updateAppointmentResult(appointmentResult.id(), payload.currentAppointmentId(), payload.nextAppointmentId(), payload.stateId(), payload.diagnosisId(), payload.advice(), payload.prescription());
            return "redirect:/clinic/appointments/results/edit";
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/appointments/results/edit";
        }
    }

    @PostMapping("delete")
    public String deleteAppointmentResult(@ModelAttribute("appointmentResult") AppointmentResult appointmentResult) {
        this.resultRestClient.deleteAppointmentResult(appointmentResult.id());
        return "clinic/appointments/results/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
