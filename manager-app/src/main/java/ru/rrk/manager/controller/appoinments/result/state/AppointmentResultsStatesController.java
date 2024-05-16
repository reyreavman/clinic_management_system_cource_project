package ru.rrk.manager.controller.appoinments.result.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.appoinments.result.state.payload.NewAppointmentResultStatePayload;
import ru.rrk.manager.entity.appointments.AppointmentResultState;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.appointment.result.state.AppointmentResultStateRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments/results/states")
public class AppointmentResultsStatesController {
    private final AppointmentResultStateRestClient restClient;

    @GetMapping("list")
    public String getStatesList(Model model) {
        model.addAttribute("states", this.restClient.findAllStates());
        return "clinic/appointments/results/states/list";
    }

    @GetMapping("create")
    public String getNewStatePage() {
        return "clinic/appointments/results/states/new_state";
    }

    @PostMapping("create")
    public String createState(NewAppointmentResultStatePayload payload, Model model) {
        try {
            AppointmentResultState state = this.restClient.createState(payload.state());
            return "redirect:/clinic/appointments/results/states/%d".formatted(state.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/appointments/results/states/new_state";
        }
    }
}
