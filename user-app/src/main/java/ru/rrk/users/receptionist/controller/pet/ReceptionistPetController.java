package ru.rrk.users.receptionist.controller.pet;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.dto.Receptionist;
import ru.rrk.common.dto.pet.Pet;
import ru.rrk.common.restClient.*;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.restClient.pet.GenderRestClient;
import ru.rrk.common.restClient.pet.PetBreedRestClient;
import ru.rrk.common.restClient.pet.PetRestClient;
import ru.rrk.common.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.viewModels.appointment.AppointmentPrimaryView;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/clinic/reception/receptionist/{receptionistId:\\d+}/pets/{petId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistPetController {
    private final ReceptionistRestClient receptionistRestClient;
    private final PetRestClient petRestClient;
    private final PetBreedRestClient petBreedRestClient;
    private final GenderRestClient genderRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;

    private final AppointmentPrimaryViewConverter appointmentPrimaryViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;

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
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream().filter(appointment -> appointment.pet().id() == petId).map(this.appointmentPrimaryViewConverter::convert).sorted(Comparator.comparing(AppointmentPrimaryView::date)).toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream().filter(checkup -> checkup.pet().id() == petId).map(this.checkupPrimaryViewConverter::convert).sorted(Comparator.comparing(CheckupPrimaryView::date)).toList());
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
