package ru.rrk.user.receptionist.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.user.receptionist.controller.payload.NewClientPayload;
import ru.rrk.user.receptionist.controller.payload.NewLabelPayload;
import ru.rrk.user.receptionist.controller.payload.NewPetPayload;
import ru.rrk.user.receptionist.dto.Client;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.dto.pet.Breed;
import ru.rrk.user.receptionist.dto.pet.Label;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.dto.pet.Type;
import ru.rrk.user.receptionist.restClient.*;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/clients")
@RequiredArgsConstructor
public class ReceptionistClientsController {
    private final ReceptionistRestClient receptionistRestClient;
    private final ClientRestClient clientRestClient;
    private final PetBreedRestClient petBreedRestClient;
    private final GenderRestClient genderRestClient;
    private final LabelRestClient labelRestClient;
    private final PetRestClient petRestClient;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping("create")
    public String getNewClientPage(Model model) {
        model.addAttribute("breeds", this.petBreedRestClient.findAllBreeds(null));
        model.addAttribute("genders", this.genderRestClient.findAllGenders());
        return "clinic/reception/receptionist/clients/create";
    }

    @GetMapping("list")
    public String getClientsListPage() {
        return "clinic/receptionist/clients/list";
    }

    @PostMapping("create")
    public String createNewClient(NewClientPayload clientPayload, NewPetPayload petPayload, NewLabelPayload labelPayload, Model model) {
        try {
            Integer typeId = this.petBreedRestClient.findBreed(petPayload.breedId()).map(Breed::type).map(Type::id).orElseThrow(NoSuchElementException::new);
            Client client = this.clientRestClient.createClient(clientPayload.firstName(), clientPayload.lastName(), clientPayload.phoneNumber(), clientPayload.email());
            Label label = this.labelRestClient.createLabel(labelPayload.value(), labelPayload.date());
            Pet pet = this.petRestClient.createPet(petPayload.name(), client.id(), typeId, petPayload.breedId(), petPayload.genderId(), petPayload.birthday(), label.id());
            return "clinic/reception/receptionist/{receptionistId}/clients/%d".formatted(pet.id());
        } catch (BadRequestException exception) {
            model.addAttribute("clientPayload", clientPayload);
            model.addAttribute("petPayload", petPayload);
            model.addAttribute("labelPayload", labelPayload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/clients/new_client";
        }
    }
}
