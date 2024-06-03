package ru.rrk.users.receptionist.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.common.restClient.*;
import ru.rrk.common.restClient.pet.GenderRestClient;
import ru.rrk.common.restClient.pet.LabelRestClient;
import ru.rrk.common.restClient.pet.PetBreedRestClient;
import ru.rrk.common.restClient.pet.PetRestClient;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.receptionist.controller.pet.payload.label.NewLabelPayload;
import ru.rrk.users.receptionist.controller.pet.payload.NewPetPayload;
import ru.rrk.users.receptionist.controller.client.payload.UpdateClientPayload;
import ru.rrk.common.dto.Client;
import ru.rrk.common.dto.Receptionist;
import ru.rrk.common.dto.pet.Breed;
import ru.rrk.common.dto.pet.Type;
import ru.rrk.common.mapper.pet.PetViewPrimaryConverter;
import ru.rrk.common.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.common.viewModels.pet.PetPrimaryView;
import ru.rrk.common.viewModels.appointment.AppointmentPrimaryView;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/clients/{clientId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistClientController {
    private final ReceptionistRestClient receptionistRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final ClientRestClient clientRestClient;
    private final PetRestClient petRestClient;
    private final PetBreedRestClient petBreedRestClient;
    private final GenderRestClient genderRestClient;
    private final LabelRestClient labelRestClient;

    private final PetViewPrimaryConverter petViewPrimaryConverter;
    private final AppointmentPrimaryViewConverter appointmentPrimaryViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @ModelAttribute("client")
    public Client client(@PathVariable("clientId") int clientId) {
        return this.clientRestClient.findClient(clientId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.client.not_found"));
    }

    @GetMapping
    public String getClientInfoPage(@PathVariable("clientId") int clientId, Model model) {
        List<PetPrimaryView> petList = this.petRestClient.findAllPets(null).stream().filter(pet -> pet.client().id() == clientId).map(this.petViewPrimaryConverter::convert).toList();
        Set<Integer> petIds = petList.stream().map(PetPrimaryView::id).collect(Collectors.toSet());
        List<AppointmentPrimaryView> appointmentList = this.appointmentRestClient.findAllAppointments().stream().filter(appointment -> petIds.contains(appointment.pet().id())).map(this.appointmentPrimaryViewConverter::convert).toList();

        model.addAttribute("pets", petList);
        model.addAttribute("appointments", appointmentList);
        return "clinic/reception/receptionist/clients/client";
    }

    @GetMapping("edit")
    public String getClientEditPage() {
        return "clinic/reception/receptionist/clients/edit";
    }

    @GetMapping("pets/create")
    public String getPetCreatePage(Model model) {
        model.addAttribute("breeds", this.petBreedRestClient.findAllBreeds(null));
        model.addAttribute("genders", this.genderRestClient.findAllGenders());
        return "clinic/reception/receptionist/pets/create";
    }

    @PostMapping("edit")
    public String editClient(@ModelAttribute(name = "client", binding = false) Client client,
                             @PathVariable("receptionistId") int receptionistId, UpdateClientPayload payload, Model model) {
        try {
            this.clientRestClient.updateClient(client.id(), payload.firstName(), payload.lastName(), payload.phoneNumber(), payload.email());
            return "redirect:/clinic/reception/receptionist/%d/clients/%d".formatted(receptionistId, client.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/reception/receptionist/clients/edit";
        }
    }

    @PostMapping("pets/create")
    public String createPet(@PathVariable("receptionistId") int receptionistId,
                            @PathVariable("clientId") int clientId,
                            NewPetPayload petPayload, NewLabelPayload labelPayload,
                            Model model) {
        try {
            Integer typeId = this.petBreedRestClient.findBreed(petPayload.breedId()).map(Breed::type).map(Type::id).orElseThrow(NoSuchElementException::new);
            Integer labelId = this.labelRestClient.createLabel(labelPayload.value(), labelPayload.date()).id();
            this.petRestClient.createPet(petPayload.name(), clientId, typeId, petPayload.breedId(), petPayload.genderId(), petPayload.birthday(), labelId);
            return "redirect:/clinic/reception/receptionist/%d/clients/%d".formatted(receptionistId, clientId);
        } catch (BadRequestException exception) {
            model.addAttribute("petPayload", petPayload);
            model.addAttribute("labelPayload", labelPayload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/reception/receptionist/pets/create";
        }
    }
}
