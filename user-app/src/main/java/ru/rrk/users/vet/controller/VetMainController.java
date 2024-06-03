package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.restClient.AppointmentRestClient;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.viewModels.vet.VetView;
import ru.rrk.common.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.viewModels.appointment.AppointmentPrimaryView;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}")
@RequiredArgsConstructor
public class VetMainController {
    private final VetRestClient vetRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;

    private final VetViewConverter vetViewConverter;
    private final AppointmentPrimaryViewConverter appointmentPrimaryViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.vet.not_found"));
    }

    @GetMapping
    public String getVetMainPage(Model model, @PathVariable("vetId") int vetId) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments()
                .stream().map(this.appointmentPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(appointment -> appointment.vetId() == vetId)
                .filter(appointment -> appointment.date().isEqual(LocalDate.now()))
                .sorted(Comparator.comparing(AppointmentPrimaryView::time))
                .toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups()
                .stream().map(this.checkupPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(checkup -> checkup.vetId() == vetId)
                .filter(checkup -> checkup.date().isEqual(LocalDate.now()))
                .sorted(Comparator.comparing(CheckupPrimaryView::time))
                .toList());
        return "clinic/vets/vet/main_page";
    }

    @GetMapping("info")
    public String getVetInfoPage(Model model, @PathVariable("vetId") int vetId) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments()
                .stream().map(this.appointmentPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(appointment -> appointment.vetId() == vetId)
                .sorted(Comparator.comparing(AppointmentPrimaryView::date).thenComparing(AppointmentPrimaryView::time))
                .toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups()
                .stream().map(this.checkupPrimaryViewConverter::convert)
                .filter(Objects::nonNull)
                .filter(checkup -> checkup.vetId() == vetId)
                .sorted(Comparator.comparing(CheckupPrimaryView::date).thenComparing(CheckupPrimaryView::time))
                .toList());
        return "clinic/vets/vet/info_page";
    }
}
