package ru.rrk.user.receptionist.controller.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentListViewConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.user.receptionist.mapper.vet.VetSummaryViewConverter;
import ru.rrk.user.receptionist.restClient.AppointmentRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.restClient.VetRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupRestClient;
import ru.rrk.user.receptionist.viewModels.appointment.AppointmentListView;
import ru.rrk.user.receptionist.viewModels.checkup.CheckupPrimaryView;
import ru.rrk.user.receptionist.viewModels.vet.VetSummaryView;

import java.util.Comparator;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/vets/{vetId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistVetController {
    private final ReceptionistRestClient receptionistRestClient;
    private final VetRestClient vetRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final CheckupRestClient checkupRestClient;

    private final AppointmentListViewConverter appointmentListViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;
    private final VetSummaryViewConverter vetSummaryViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.reception.errors.receptionist.not_found"));
    }

    @ModelAttribute("vet")
    public VetSummaryView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetSummaryViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.reception.errors.vet.not_found"));
    }

    @GetMapping
    public String getVetInfoPage(Model model, @PathVariable("vetId") int vetId) {
        model.addAttribute("appointments", this.appointmentRestClient.findAllAppointments().stream()
                .map(this.appointmentListViewConverter::convert)
                .filter(appointment -> appointment.vetId() == vetId)
                .sorted(Comparator.comparing(AppointmentListView::date))
                .toList());
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream()
                .map(this.checkupPrimaryViewConverter::convert)
                .filter(checkup -> checkup.vetId() == vetId)
                .sorted(Comparator.comparing(CheckupPrimaryView::date))
                .toList());
        return "clinic/reception/receptionist/vets/vet";
    }
}
