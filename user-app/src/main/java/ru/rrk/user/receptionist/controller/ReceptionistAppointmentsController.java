package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentViewPrimaryConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/appointments")
@RequiredArgsConstructor
public class ReceptionistAppointmentsController {
    private final AppointmentRestClient appointmentRestClient;
    private final AppointmentViewPrimaryConverter appointmentViewPrimaryConverter;

    @GetMapping("create")
    public String getNewAppointmentPage() {
        return "clinic/reception/receptionist/appointments/create";
    }

    @GetMapping("list")
    public String getAppointmentsListPage(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream().map(this.appointmentViewPrimaryConverter::convert).toList());

        return "clinic/reception/receptionist/appointments/list";
    }
}
