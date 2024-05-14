package ru.rrk.manager.controller.pets.pet;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.pets.pet.payload.UpdatePetPayload;
import ru.rrk.manager.entity.pet.Pet;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.client.ClientRestClient;
import ru.rrk.manager.restClients.gender.GenderRestClient;
import ru.rrk.manager.restClients.pet.breed.PetBreedRestClient;
import ru.rrk.manager.restClients.pet.label.LabelRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/pets/{petId:\\d+}")
public class PetController {
    private final PetRestClient petRestClient;
    private final ClientRestClient clientRestClient;
    private final PetTypeRestClient typeRestClient;
    private final PetBreedRestClient breedRestClient;
    private final GenderRestClient genderRestClient;
    private final LabelRestClient labelRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("pet")
    public Pet pet(@PathVariable("petId") int petId) {
        return this.petRestClient.findPet(petId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.pet.not_found"));
    }

    @GetMapping
    public String getPet() {
        return "clinic/pets/pet";
    }

    @GetMapping("edit")
    public String getPetEditPage(Model model) {
        model.addAttribute("clients", this.clientRestClient.findAllClients(null));
        model.addAttribute("types", this.typeRestClient.findAllTypes(null));
        model.addAttribute("breeds", this.breedRestClient.findAllBreeds(null));
        model.addAttribute("genders", this.genderRestClient.findAllGenders());
        model.addAttribute("labels", this.labelRestClient.findAllLabels());
        return "clinic/pets/edit";
    }

    @PostMapping("edit")
    public String updatePet(@ModelAttribute(name = "pet", binding = false) Pet pet,
                            UpdatePetPayload payload,
                            Model model) {
        try {
            this.petRestClient.updatePet(pet.id(), payload.name(), payload.clientId(), payload.typeId(), payload.breedId(), payload.genderId(), payload.birthday(), payload.labelId());
            return "redirect:/clinic/pets/%d".formatted(pet.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/pets/edit";
        }
    }

    @PostMapping("delete")
    public String deletePet(@ModelAttribute("pet") Pet pet) {
        this.petRestClient.deletePet(pet.id());
        return "redirect:/clinic/pets/list";
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
