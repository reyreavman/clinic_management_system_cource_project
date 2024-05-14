package ru.rrk.manager.controller.checkups.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.checkups.checkup.payload.NewCheckupPayload;
import ru.rrk.manager.entity.checkup.Checkup;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.checkup.CheckupRestClient;
import ru.rrk.manager.restClients.checkup.result.CheckupResultRestClient;
import ru.rrk.manager.restClients.checkup.state.CheckupStateRestClient;
import ru.rrk.manager.restClients.checkup.type.CheckupTypeRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.vet.vet.VetRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/checkups")
public class CheckupsController {
    private final CheckupRestClient checkupRestClient;
    private final PetRestClient petRestClient;
    private final VetRestClient vetRestClient;
    private final CheckupTypeRestClient typeRestClient;
    private final CheckupStateRestClient stateRestClient;
    private final CheckupResultRestClient resultRestClient;

    @GetMapping("list")
    public String getCheckupsList(Model model) {
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups());
        return "clinic/checkups/list";
    }

    @GetMapping("create")
    public String getNewCheckupPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null));
        model.addAttribute("vets", this.vetRestClient.findAllVets(null));
        model.addAttribute("checkupTypes", this.typeRestClient.findAllTypes());
        model.addAttribute("checkupStates", this.stateRestClient.findAllStates());
        model.addAttribute("checkupResults", this.resultRestClient.findAllResults());
        return "clinic/checkups/new_checkup";
    }

    @PostMapping("create")
    public String createCheckup(NewCheckupPayload payload, Model model) {
        try {
            Checkup checkup = this.checkupRestClient.createCheckup(payload.date(), payload.time(), payload.petId(), payload.vetId(), payload.checkupTypeId(), payload.checkupStateId(), payload.checkupResultId());
            return "redirect:/clinic/checkups/%d".formatted(checkup.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/new_checkup";
        }
    }
}
