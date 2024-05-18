package ru.rrk.manager.controller.appoinments.results.state;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.appoinments.results.state.payload.UpdateAppointmentResultStatePayload;
import ru.rrk.manager.entity.appointments.AppointmentResultState;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.appointment.result.state.AppointmentResultStateRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments/results/states/{stateId:\\d+}")
public class AppointmentResultsStateController {
    private final AppointmentResultStateRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("state")
    public AppointmentResultState state(@PathVariable("stateId") int stateId) {
        return this.restClient.findState(stateId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.appointment.result.state.not_found"));
    }

    @GetMapping
    public String getState() {
        return "clinic/appointments/results/states/state";
    }

    @GetMapping("edit")
    public String getStateEditPage() {
        return "clinic/appointments/results/states/edit";
    }

    @PostMapping("edit")
    public String updateState(@ModelAttribute(name = "state", binding = false) AppointmentResultState state,
                              UpdateAppointmentResultStatePayload payload, Model model) {
        try {
            this.restClient.updateState(state.id(), payload.state());
            return "redirect:/clinic/appointments/results/states/%d" .formatted(state.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/appointments/results/states/edit";
        }
    }

    @PostMapping("delete")
    public String deleteState(@ModelAttribute("state") AppointmentResultState state) {
        this.restClient.deleteState(state.id());
        return "redirect:/clinic/appointments/results/states/list";
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
