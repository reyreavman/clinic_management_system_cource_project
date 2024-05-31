package ru.rrk.user.receptionist.controller.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentListViewConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentListView;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/appointments")
@RequiredArgsConstructor
public class ReceptionistAppointmentsController {
    private final ReceptionistRestClient receptionistRestClient;
    private final AppointmentRestClient appointmentRestClient;

    private final AppointmentListViewConverter appointmentListViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping("list")
    public String getAppointmentsListPage(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream().map(this.appointmentListViewConverter::convert).sorted(Comparator.comparing(AppointmentListView::date)).toList());
        return "clinic/reception/receptionist/appointments/list";
    }
}
