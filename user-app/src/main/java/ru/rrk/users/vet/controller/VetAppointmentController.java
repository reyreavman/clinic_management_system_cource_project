package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.AppointmentRestClient;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.viewModels.appointment.AppointmentPrimaryView;
import ru.rrk.common.viewModels.vet.VetView;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/appointments/{appointmentId:\\d+}")
@RequiredArgsConstructor
public class VetAppointmentController {
    private final VetRestClient vetRestClient;
    private final AppointmentRestClient appointmentRestClient;

    private final VetViewConverter vetViewConverter;
    private final AppointmentPrimaryViewConverter appointmentPrimaryViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.vet.not_found"));
    }

    @ModelAttribute("appointment")
    public AppointmentPrimaryView appointment(@PathVariable("appointmentId") int appointmentId) {
        return this.appointmentRestClient.findAppointment(appointmentId).map(this.appointmentPrimaryViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.appointment.not_found"));
    }

    @GetMapping
    public String getAppointmentInfoPage(@ModelAttribute("appointment") AppointmentPrimaryView appointment,
                                         Model model) {
        if (appointment.checkup() != null) model.addAttribute("checkup", this.checkupPrimaryViewConverter.convert(appointment.checkup()));
        return "clinic/vets/appointments/appointment";
    }
}
