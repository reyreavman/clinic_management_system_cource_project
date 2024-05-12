package ru.rrk.manager.controller.pet.breeds;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.pet.breeds.payload.UpdatePetBreedPayload;
import ru.rrk.manager.entity.pet.PetBreed;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.pet.breed.PetBreedRestClient;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/pets/breeds/{breedId:\\d+}")
public class PetBreedController {
    private final PetBreedRestClient breedRestClient;
    private final PetTypeRestClient typeRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("breed")
    public PetBreed breed(@PathVariable("breedId") int breedId) {
        return this.breedRestClient.findBreed(breedId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.pet.breed.not_found"));
    }

    @GetMapping
    public String getBreed() {
        return "clinic/pets/breeds/breed";
    }

    @GetMapping("edit")
    public String getBreedEditPage(Model model) {
        model.addAttribute("petTypes", this.typeRestClient.findAllTypes(null));
        return "clinic/pets/breeds/edit";
    }

    @PostMapping("edit")
    public String updateBreed(@ModelAttribute(name = "breed", binding = false) PetBreed breed,
                              UpdatePetBreedPayload payload,
                              Model model) {
        try {
            this.breedRestClient.updateBreed(breed.id(), payload.name(), payload.petTypeId());
            return "redirect:/clinic/pets/breeds/%d".formatted(breed.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/vets/edit";
        }
    }

    @PostMapping("delete")
    public String deleteBreed(@ModelAttribute("breed") PetBreed breed) {
        this.breedRestClient.deleteBreed(breed.id());
        return "redirect:/clinic/pets/breeds/list";
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
