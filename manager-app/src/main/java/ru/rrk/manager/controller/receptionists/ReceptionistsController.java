package ru.rrk.manager.controller.receptionists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.receptionists.payload.NewReceptionistPayload;
import ru.rrk.manager.entity.Receptionist;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.receptionist.ReceptionistRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/receptionists")
public class ReceptionistsController {
    private final ReceptionistRestClient restClient;

    @GetMapping("list")
    public String getReceptionistsList(Model model) {
        model.addAttribute("receptionists", this.restClient.findAllReceptionists());
        model.addAttribute("filter");
        return "clinic/receptionists/list";
    }

    @GetMapping("create")
    public String getNewReceptionistPage() {
        return "clinic/receptionists/new_receptionist";
    }

    @PostMapping("create")
    public String createReceptionist(NewReceptionistPayload payload, Model model) {
        try {
            Receptionist receptionist = this.restClient.createReceptionist(payload.firstName(), payload.lastName());
            return "redirect:/clinic/receptionists/%d".formatted(receptionist.getId());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/receptionists/new_receptionist";
        }
    }

}
