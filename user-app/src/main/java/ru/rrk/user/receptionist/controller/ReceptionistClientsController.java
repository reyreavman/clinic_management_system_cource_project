package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.user.receptionist.controller.payload.NewClientPayload;
import ru.rrk.user.receptionist.controller.payload.NewLabelPayload;
import ru.rrk.user.receptionist.controller.payload.NewPetPayload;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.dto.pet.Breed;
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
    public String getClientsListPage(Model model) {
        model.addAttribute("clients", this.clientRestClient.findAllClients(null));
        return "clinic/reception/receptionist/clients/list";
    }

    @PostMapping("create")
    public String createNewClient(@PathVariable("receptionistId") int receptionistId,
                                  NewClientPayload clientPayload, NewPetPayload petPayload,
                                  NewLabelPayload labelPayload, Model model) {
        try {
            Integer typeId = this.petBreedRestClient.findBreed(petPayload.breedId()).map(Breed::type).map(Type::id).orElseThrow(NoSuchElementException::new);
            Integer labelId = this.labelRestClient.createLabel(labelPayload.value(), labelPayload.date()).id();
            Integer clientId = this.clientRestClient.createClient(clientPayload.firstName(), clientPayload.lastName(), clientPayload.phoneNumber(), clientPayload.email()).id();
            this.petRestClient.createPet(petPayload.name(), clientId, typeId, petPayload.breedId(), petPayload.genderId(), petPayload.birthday(), labelId);
            return "redirect:/clinic/reception/receptionist/%d/clients/%d".formatted(receptionistId, clientId);
        } catch (BadRequestException exception) {
            model.addAttribute("clientPayload", clientPayload);
            model.addAttribute("petPayload", petPayload);
            model.addAttribute("labelPayload", labelPayload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/clients/new_client";
        }
    }
}
