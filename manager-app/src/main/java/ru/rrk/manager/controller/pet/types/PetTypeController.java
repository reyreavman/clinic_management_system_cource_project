package ru.rrk.manager.controller.pet.types;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.pet.types.payload.UpdatePetTypePayload;
import ru.rrk.manager.entity.pet.PetType;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/pets/types/{typeId:\\d+}")
@RequiredArgsConstructor
public class PetTypeController {
    private final PetTypeRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("type")
    public PetType type(@PathVariable("typeId") int typeId) {
        return this.restClient.findType(typeId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.pet.type.not_found"));
    }

    @GetMapping
    public String getType() {
        return "clinic/pets/types/type";
    }

    @GetMapping("edit")
    public String getTypeEditPage() {
        return "clinic/pets/types/edit";
    }

    @PostMapping("edit")
    public String updateType(@ModelAttribute(name = "type", binding = false) PetType type,
                             UpdatePetTypePayload payload, Model model) {
        try {
            this.restClient.updateType(type.id(), payload.name());
            return "redirect:/clinic/pets/types/%d".formatted(type.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/clients/edit";
        }
    }

    @PostMapping("delete")
    public String deleteType(@ModelAttribute("type") PetType type) {
        this.restClient.deleteType(type.id());
        return "redirect:/clinic/pets/types/list";
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
