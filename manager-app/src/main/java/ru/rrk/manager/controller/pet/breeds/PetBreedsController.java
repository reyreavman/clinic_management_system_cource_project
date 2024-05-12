package ru.rrk.manager.controller.pet.breeds;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rrk.manager.controller.pet.breeds.payload.NewPetBreedPayload;
import ru.rrk.manager.entity.pet.PetBreed;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.pet.breed.PetBreedRestClient;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/pets/breeds")
public class PetBreedsController {
    private final PetBreedRestClient breedRestClient;
    private final PetTypeRestClient typeRestClient;

    @GetMapping("list")
    public String getBreedsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("breeds", this.breedRestClient.findAllBreeds(filter));
        model.addAttribute("filter", filter);
        return "clinic/pets/breeds/list";
    }

    @GetMapping("create")
    public String getNewBreedPage(Model model) {
        model.addAttribute("petTypes", this.typeRestClient.findAllTypes(null));
        return "clinic/pets/breeds/new_breed";
    }

    @PostMapping("create")
    public String createBreed(NewPetBreedPayload payload, Model model) {
        try {
            System.out.println(payload.typeId());
            PetBreed breed = this.breedRestClient.createBreed(payload.name(), payload.typeId());
            return "redirect:/clinic/pets/breeds/%d".formatted(breed.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/pets/breeds/new_breed";
        }
    }
}
