package ru.rrk.user.receptionist.controller.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentPrimaryView;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/appointments/{appointmentId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistAppointmentController {
    private final ReceptionistRestClient receptionistRestClient;
    private final AppointmentRestClient appointmentRestClient;

    private final AppointmentPrimaryViewConverter appointmentPrimaryViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @ModelAttribute("appointment")
    public AppointmentPrimaryView appointment(@PathVariable("appointmentId") int appointmentId) {
        return this.appointmentRestClient.findAppointment(appointmentId).map(this.appointmentPrimaryViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.appointment.not_found"));

    }

    @GetMapping
    public String getAppointmentInfoPage(@ModelAttribute("appointment") AppointmentPrimaryView appointment, Model model) {
        model.addAttribute("checkup", this.checkupPrimaryViewConverter.convert(appointment.checkup()));
        return "clinic/reception/receptionist/appointments/appointment";
    }

    @PostMapping("delete")
    public String deleteAppointment(@ModelAttribute("appointment") AppointmentPrimaryView appointment,
                                    @PathVariable("receptionistId") int receptionistId) {
        this.appointmentRestClient.deleteAppointment(appointment.id());
        return "redirect:/clinic/reception/receptionist/%d/appointments/list".formatted(receptionistId);
    }
}
