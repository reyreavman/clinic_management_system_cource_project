package ru.rrk.manager.controller.vets.specialities;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.vets.specialities.payload.UpdateSpecialityPayload;
import ru.rrk.manager.entity.vet.Speciality;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.vet.speciality.SpecialityRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/specialities/{specialityId:\\d+}")
@RequiredArgsConstructor
public class SpecialityController {
    private final SpecialityRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("speciality")
    public Speciality speciality(@PathVariable("specialityId") int specialityId) {
        return this.restClient.findSpeciality(specialityId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.speciality.not_found"));
    }

    @GetMapping
    public String getSpeciality() {
        return "clinic/vets/specialities/speciality";
    }

    @GetMapping("edit")
    public String getSpecialityEditPage() {
        return "clinic/vets/specialities/edit";
    }

    @PostMapping("edit")
    public String updateSpeciality(@ModelAttribute(name = "speciality", binding = false) Speciality speciality,
                                   UpdateSpecialityPayload payload, Model model) {
        try {
            this.restClient.updateSpeciality(speciality.id(), payload.name());
            return "redirect:/clinic/vets/specialities/%d".formatted(speciality.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/vets/specialities/edit";
        }
    }

    @PostMapping("delete")
    public String deleteSpeciality(@ModelAttribute("speciality") Speciality speciality) {
        this.restClient.deleteSpeciality(speciality.id());
        return "redirect:/clinic/vets/specialities/list";
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
