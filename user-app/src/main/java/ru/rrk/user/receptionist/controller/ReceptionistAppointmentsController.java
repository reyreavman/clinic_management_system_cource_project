package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Appointment;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentViewPrimaryConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/appointments/{appointmentId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistAppointmentsController {
    private final ReceptionistRestClient receptionistRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final AppointmentViewPrimaryConverter appointmentViewPrimaryConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @ModelAttribute("appointment")
    public Appointment appointment(@PathVariable("appointmentId") int appointmentId) {
        return this.appointmentRestClient.findAppointment(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.appointment.not_found"));
    }

    @GetMapping
    public String getAppointmentInfoPage() {
        return "clinic/reception/receptionist/appointments/appointment";
    }
}
