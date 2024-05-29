package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentViewSummaryConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupViewSummaryConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupRestClient;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentViewSummary;
import ru.rrk.user.receptionist.viewModels.checkup.CheckupViewSummary;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}")
@RequiredArgsConstructor()
public class ReceptionistMainController {
    private final ReceptionistRestClient receptionistRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;
    private final AppointmentViewSummaryConverter appointmentViewSummaryConverter;
    private final CheckupViewSummaryConverter checkupViewSummaryConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping
    public String getReceptionistMainPage(Model model) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream().map(this.appointmentViewSummaryConverter::convert).sorted(Comparator.comparing(AppointmentViewSummary::time)).toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream().map(this.checkupViewSummaryConverter::convert).sorted(Comparator.comparing(CheckupViewSummary::time)).toList());
        return "clinic/reception/receptionist/main_page";
    }

    @GetMapping("info")
    public String getReceptionistInfoPage() {
        return "clinic/reception/receptionist/info_page";
    }
}
