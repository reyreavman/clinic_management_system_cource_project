package ru.rrk.user.receptionist.controller.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.user.receptionist.controller.BadRequestException;
import ru.rrk.user.receptionist.controller.appointment.payload.NewAppointmentPayload;
import ru.rrk.user.receptionist.dto.Appointment;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.dto.pet.Pet;
import ru.rrk.user.receptionist.mapper.vet.VetSummaryViewConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;
import ru.rrk.user.receptionist.restClient.PetRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.restClient.VetRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/clinic/reception/receptionist/{receptionistId:\\d+}/pets/{petId:\\d+}/appointments")
@RequiredArgsConstructor
public class ReceptionistAppointmentByPetController {
    private final ReceptionistRestClient receptionistRestClient;
    private final VetRestClient vetRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final PetRestClient petRestClient;

    private final VetSummaryViewConverter vetSummaryViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @ModelAttribute("pet")
    public Pet pet(@PathVariable("petId") int petId) {
        return this.petRestClient.findPet(petId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping("create")
    public String getNewAppointmentPage(Model model) {
        model.addAttribute("vets", this.vetRestClient.findAllVets(null).stream().map(this.vetSummaryViewConverter::convert).toList());
        return "clinic/reception/receptionist/appointments/create";
    }

    @PostMapping("create")
    public String createPetAppointment(NewAppointmentPayload payload, Model model,
                                       @PathVariable("petId") int petId,
                                       @PathVariable("receptionistId") int receptionistId) {
        try {
            Appointment appointment = this.appointmentRestClient.createAppointment(
                    petId,
                    payload.vetId(),
                    payload.date(),
                    payload.time(),
                    payload.description(),
                    null,
                    receptionistId
            );
            return "redirect:/clinic/reception/receptionist/%d/appointments/%d".formatted(receptionistId, appointment.id());
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "clinic/reception/receptionist/checkups/create";
        }
    }
}
