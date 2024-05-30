package ru.rrk.user.receptionist.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.dto.pet.Breed;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.dto.pet.Type;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentViewPrimaryConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupViewPrimaryConverter;
import ru.rrk.user.receptionist.restClient.*;
import ru.rrk.user.receptionist.restClient.checkup.CheckupRestClient;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentViewPrimary;
import ru.rrk.user.receptionist.viewModels.checkup.CheckupViewPrimary;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/clinic/reception/receptionist/{receptionistId:\\d+}/pets/{petId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistPetController {
    private final ReceptionistRestClient receptionistRestClient;
    private final PetRestClient petRestClient;
    private final PetBreedRestClient petBreedRestClient;
    private final GenderRestClient genderRestClient;
    private final LabelRestClient labelRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;

    private final AppointmentViewPrimaryConverter appointmentViewPrimaryConverter;
    private final CheckupViewPrimaryConverter checkupViewPrimaryConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.reception.receptionist.not_found"));
    }

    @ModelAttribute("pet")
    public Pet pet(@PathVariable("petId") int petId) {
        return this.petRestClient.findPet(petId)
                .orElseThrow(() -> new NoSuchElementException("clinic.reception.pet.not_found"));
    }

    @GetMapping
    public String getPetInfoPage(@PathVariable("petId") int petId, Model model, HttpServletRequest request) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream().filter(appointment -> appointment.pet().id() == petId).map(this.appointmentViewPrimaryConverter::convert).sorted(Comparator.comparing(AppointmentViewPrimary::date)).toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream().filter(checkup -> checkup.pet().id() == petId).map(this.checkupViewPrimaryConverter::convert).sorted(Comparator.comparing(CheckupViewPrimary::date)).toList());
        model.addAttribute("request", request.getHeader("referer"));
        return "clinic/reception/receptionist/pets/pet";
    }

    @GetMapping("edit")
    public String getPetEditPage(Model model) {
        model.addAttribute("breeds", this.petBreedRestClient.findAllBreeds(null));
        model.addAttribute("genders", this.genderRestClient.findAllGenders());
        return "clinic/reception/receptionist/pets/edit";
    }
}
