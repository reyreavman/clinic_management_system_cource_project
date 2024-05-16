package ru.rrk.manager.controller.appoinments.appointment;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.appoinments.appointment.payload.UpdateAppointmentPayload;
import ru.rrk.manager.entity.appointments.Appointment;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.appointment.appointment.AppointmentRestClient;
import ru.rrk.manager.restClients.checkup.checkup.CheckupRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.receptionist.ReceptionistRestClient;
import ru.rrk.manager.restClients.vet.vet.VetRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments/{appointmentId:\\d+}")
public class AppointmentController {
    private final AppointmentRestClient appointmentRestClient;
    private final PetRestClient petRestClient;
    private final VetRestClient vetRestClient;
    private final CheckupRestClient checkupRestClient;
    private final ReceptionistRestClient receptionistRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("appointment")
    public Appointment appointment(@PathVariable("appointmentId") int appointmentId) {
        return this.appointmentRestClient.findAppointment(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("clinic.error.appointment.not_found"));
    }

    @GetMapping
    public String getAppointment() {
        return "clinic/appointments/appointment";
    }

    @GetMapping("edit")
    public String getAppointmentEditPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null));
        model.addAttribute("vets", this.vetRestClient.findAllVets(null));
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups());
        model.addAttribute("receptionists", this.receptionistRestClient.findAllReceptionists());
        return "clinic/appointments/edit";
    }

    @PostMapping("edit")
    public String updateAppointment(@ModelAttribute(name = "appointment", binding = false) Appointment appointment,
                                    UpdateAppointmentPayload payload, Model model) {
        try {
            System.out.println(payload.receptionistId());
            this.appointmentRestClient.updateAppointment(appointment.id(), payload.petId(), payload.vetId(), payload.date(), payload.time(), payload.description(), payload.checkupId(), payload.receptionistId());
            return "redirect:/clinic/appointments/%d".formatted(appointment.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/appointments/edit";
        }
    }

    @PostMapping("delete")
    public String deleteAppointment(@ModelAttribute("appointment") Appointment appointment) {
        this.appointmentRestClient.deleteAppointment(appointment.id());
        return "redirect:/clinic/appointments/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}