package ru.rrk.manager.controller.pets.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rrk.manager.controller.pets.pet.payload.NewPetPayload;
import ru.rrk.manager.entity.pet.Pet;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.client.ClientRestClient;
import ru.rrk.manager.restClients.gender.GenderRestClient;
import ru.rrk.manager.restClients.pet.breed.PetBreedRestClient;
import ru.rrk.manager.restClients.pet.label.LabelRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/pets")
public class PetsController {
    private final PetRestClient petRestClient;
    private final ClientRestClient clientRestClient;
    private final PetTypeRestClient typeRestClient;
    private final PetBreedRestClient breedRestClient;
    private final GenderRestClient genderRestClient;
    private final LabelRestClient labelRestClient;

    @GetMapping("list")
    public String getPetsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("pets", this.petRestClient.findAllPets(filter));
        model.addAttribute("filter", filter);
        return "clinic/pets/list";
    }

    @GetMapping("create")
    public String getNewPetPage(Model model) {
        model.addAttribute("clients", this.clientRestClient.findAllClients(null));
        model.addAttribute("types", this.typeRestClient.findAllTypes(null));
        model.addAttribute("breeds", this.breedRestClient.findAllBreeds(null));
        model.addAttribute("genders", this.genderRestClient.findAllGenders());
        model.addAttribute("labels", this.labelRestClient.findAllLabels());
        return "clinic/pets/new_pet";
    }

    @PostMapping("create")
    public String createPet(NewPetPayload payload, Model model) {
        try {
            Pet pet = this.petRestClient.createPet(payload.name(), payload.clientId(), payload.typeId(), payload.breedId(), payload.genderId(), payload.birthday(), payload.labelId());
            return "redirect:/clinic/pets/%d".formatted(pet.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/pets/new_pet";
        }
    }
}
