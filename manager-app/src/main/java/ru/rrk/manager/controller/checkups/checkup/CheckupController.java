package ru.rrk.manager.controller.checkups.checkup;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.checkups.checkup.payload.UpdateCheckupPayload;
import ru.rrk.manager.entity.checkup.Checkup;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.checkup.CheckupRestClient;
import ru.rrk.manager.restClients.checkup.result.CheckupResultRestClient;
import ru.rrk.manager.restClients.checkup.state.CheckupStateRestClient;
import ru.rrk.manager.restClients.checkup.type.CheckupTypeRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.vet.vet.VetRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/checkups/{checkupId:\\d+}")
public class CheckupController {
    private final CheckupRestClient checkupRestClient;
    private final PetRestClient petRestClient;
    private final VetRestClient vetRestClient;
    private final CheckupTypeRestClient typeRestClient;
    private final CheckupStateRestClient stateRestClient;
    private final CheckupResultRestClient resultRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("checkup")
    public Checkup checkup(@PathVariable("checkupId") int checkupId) {
        return this.checkupRestClient.findCheckup(checkupId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.not_found"));
    }

    @GetMapping
    public String getCheckup() {
        return "clinic/checkups/checkup";
    }

    @GetMapping("edit")
    public String getCheckupEditPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null));
        model.addAttribute("vets", this.vetRestClient.findAllVets(null));
        model.addAttribute("checkupTypes", this.typeRestClient.findAllTypes());
        model.addAttribute("checkupStates", this.stateRestClient.findAllStates());
        model.addAttribute("checkupResults", this.resultRestClient.findAllResults());
        return "clinic/checkups/edit";
    }

    @PostMapping("edit")
    public String updateCheckup(@ModelAttribute(name = "checkup", binding = false) Checkup checkup,
                                UpdateCheckupPayload payload, Model model) {
        try {
            this.checkupRestClient.updateCheckup(checkup.id(), payload.date(), payload.time(), payload.petId(), payload.vetId(), payload.checkupTypeId(), payload.checkupStateId(), payload.checkupResultId());
            return "redirect:/clinic/checkups/%d".formatted(checkup.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/edit";
        }
    }

    @PostMapping("delete")
    public String deleteCheckup(@ModelAttribute("checkup") Checkup checkup) {
        this.checkupRestClient.deleteCheckup(checkup.id());
        return "redirect:/clinic/checkups/list";
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
