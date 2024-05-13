package ru.rrk.manager.controller.checkup.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.checkup.state.payload.NewCheckupStatePayload;
import ru.rrk.manager.entity.checkup.CheckupState;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.state.CheckupStateRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/checkups/states")
public class CheckupStatesController {
    private final CheckupStateRestClient restClient;

    @GetMapping("list")
    public String getStatesList(Model model) {
        model.addAttribute("states", this.restClient.findAllStates());
        return "clinic/checkups/states/list";
    }

    @GetMapping("create")
    public String getNewStatePage() {
        return "clinic/checkups/states/new_state";
    }

    @PostMapping("create")
    public String createState(NewCheckupStatePayload payload, Model model) {
        try {
            CheckupState state = this.restClient.createState(payload.state());
            return "redirect:/clinic/checkups/states/%d".formatted(state.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/states/new_state";
        }
    }
}
