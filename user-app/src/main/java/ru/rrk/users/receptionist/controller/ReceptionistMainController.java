package ru.rrk.users.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.dto.Receptionist;
import ru.rrk.common.mapper.appointment.AppointmentSummaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupSummaryViewConverter;
import ru.rrk.common.restClient.AppointmentRestClient;
import ru.rrk.common.restClient.ReceptionistRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.viewModels.appointment.AppointmentSummaryView;
import ru.rrk.common.viewModels.checkup.CheckupSummaryView;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}")
@RequiredArgsConstructor()
public class ReceptionistMainController {
    private final ReceptionistRestClient receptionistRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;

    private final AppointmentSummaryViewConverter appointmentSummaryViewConverter;
    private final CheckupSummaryViewConverter checkupSummaryViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping
    public String getReceptionistMainPage(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream().map(this.appointmentSummaryViewConverter::convert).sorted(Comparator.comparing(AppointmentSummaryView::time)).toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream().map(this.checkupSummaryViewConverter::convert).sorted(Comparator.comparing(CheckupSummaryView::time)).toList());
        return "clinic/reception/receptionist/main_page";
    }

    @GetMapping("info")
    public String getReceptionistInfoPage() {
        return "clinic/reception/receptionist/info_page";
    }
}
