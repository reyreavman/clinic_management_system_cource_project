package ru.rrk.manager.controller.vets;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.vets.payload.UpdateVetPayload;
import ru.rrk.manager.entity.Vet;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.speciality.SpecialityRestClient;
import ru.rrk.manager.restClients.vet.VetRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/{vetId:\\d+}")
@RequiredArgsConstructor
public class VetController {
    private final VetRestClient vetRestClient;
    private final SpecialityRestClient specialityRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("vet")
    public Vet vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vet.not_found"));
    }

    @GetMapping
    public String getVet() {
        return "clinic/vets/vet";
    }

    @GetMapping("edit")
    public String getVetEditPage(Model model) {
        model.addAttribute("specialities", this.specialityRestClient.findAllSpecialities(null));
        return "clinic/vets/edit";
    }

    @PostMapping("edit")
    public String updateVet(@ModelAttribute(name = "vet", binding = false) Vet vet,
                            UpdateVetPayload payload,
                            Model model) {
        try {
            this.vetRestClient.updateVet(vet.getId(), payload.firstName(), payload.lastName(), payload.speciality_id());
            return "redirect:/clinic/vets/%d".formatted(vet.getId());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/vets/edit";
        }
    }

    @PostMapping("delete")
    public String deleteVet(@ModelAttribute("vet") Vet vet) {
        this.vetRestClient.deleteVet(vet.getId());
        return "redirect:/clinic/vets/list";
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
