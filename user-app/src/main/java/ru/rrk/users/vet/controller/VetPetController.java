package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.mapper.appointment.AppointmentListViewConverter;
import ru.rrk.common.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.mapper.pet.PetViewPrimaryConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.AppointmentRestClient;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.restClient.pet.PetRestClient;
import ru.rrk.common.viewModels.appointment.AppointmentListView;
import ru.rrk.common.viewModels.appointment.AppointmentPrimaryView;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;
import ru.rrk.common.viewModels.pet.PetPrimaryView;
import ru.rrk.common.viewModels.vet.VetView;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/pets/{petId:\\d+}")
@RequiredArgsConstructor
public class VetPetController {
    private final VetRestClient vetRestClient;
    private final PetRestClient petRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;

    private final VetViewConverter vetViewConverter;
    private final PetViewPrimaryConverter petViewPrimaryConverter;
    private final AppointmentPrimaryViewConverter appointmentPrimaryViewConverter;
    private final AppointmentListViewConverter appointmentListViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;


    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.vet.not_found"));
    }

    @ModelAttribute("pet")
    public PetPrimaryView pet(@PathVariable("petId") int petId) {
        return this.petRestClient.findPet(petId).map(this.petViewPrimaryConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.pet.not_found"));
    }

    @GetMapping
    public String getPetInfoPage(Model model,
                                 @PathVariable("vetId") int vetId,
                                 @PathVariable("petId") int petId) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream()
                .map(this.appointmentPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(appointment -> appointment.petId() == petId)
                .filter(appointment -> appointment.vetId() == vetId)
                .sorted(Comparator.comparing(AppointmentPrimaryView::date).thenComparing(AppointmentPrimaryView::time))
                .toList());

        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream()
                .map(this.checkupPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(checkup -> checkup.petId() == petId)
                .filter(checkup -> checkup.vetId() == vetId)
                .sorted(Comparator.comparing(CheckupPrimaryView::date).thenComparing(CheckupPrimaryView::time))
                .toList());
        return "clinic/vets/pets/pet";
    }

    @GetMapping("appointments")
    public String getAllAppointments(Model model,
                                     @PathVariable("petId") int petId) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream()
                .map(this.appointmentListViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(checkup -> checkup.petId() == petId)
                .sorted(Comparator.comparing(AppointmentListView::date).thenComparing(AppointmentListView::time))
                .toList());
        return "clinic/vets/appointments/list";
    }

    @GetMapping("checkups")
    public String getAllCheckups(Model model,
                                 @PathVariable("petId") int petId) {
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream()
                .map(this.checkupPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(checkup -> checkup.petId() == petId)
                .sorted(Comparator.comparing(CheckupPrimaryView::date).thenComparing(CheckupPrimaryView::time))
                .toList());
        return "clinic/vets/checkups/list";
    }
}
