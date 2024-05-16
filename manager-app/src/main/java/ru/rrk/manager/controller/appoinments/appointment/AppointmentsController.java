package ru.rrk.manager.controller.appoinments.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.appoinments.appointment.payload.NewAppointmentPayload;
import ru.rrk.manager.entity.appointments.Appointment;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.appointment.appointment.AppointmentRestClient;
import ru.rrk.manager.restClients.checkup.checkup.CheckupRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.receptionist.ReceptionistRestClient;
import ru.rrk.manager.restClients.vet.vet.VetRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments")
public class AppointmentsController {
    private final AppointmentRestClient appointmentRestClient;
    private final PetRestClient petRestClient;
    private final VetRestClient vetRestClient;
    private final CheckupRestClient checkupRestClient;
    private final ReceptionistRestClient receptionistRestClient;

    @GetMapping("list")
    public String getAppointmentsList(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments());
        return "clinic/appointments/list";
    }

    @GetMapping("create")
    public String getNewAppointmentPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null));
        model.addAttribute("vets", this.vetRestClient.findAllVets(null));
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups());
        model.addAttribute("receptionists", this.receptionistRestClient.findAllReceptionists());
        return "clinic/appointments/new_appointment";
    }

    @PostMapping("create")
    public String createAppointment(NewAppointmentPayload payload, Model model) {
        try {
            System.out.println(payload.receptionistId());
            Appointment appointment = this.appointmentRestClient.createAppointment(payload.petId(), payload.vetId(), payload.date(), payload.time(), payload.description(), payload.checkupId(), payload.receptionistId());
            return "redirect:/clinic/appointments/%d".formatted(appointment.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/appointments/new_appointment";
        }
    }
}
