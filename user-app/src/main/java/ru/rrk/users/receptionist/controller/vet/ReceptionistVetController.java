package ru.rrk.users.receptionist.controller.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.dto.Receptionist;
import ru.rrk.common.mapper.appointment.AppointmentListViewConverter;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.AppointmentRestClient;
import ru.rrk.common.restClient.ReceptionistRestClient;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.viewModels.appointment.AppointmentListView;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;
import ru.rrk.common.viewModels.vet.VetView;

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
    private final VetViewConverter vetViewConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.reception.errors.receptionist.not_found"));
    }

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
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
