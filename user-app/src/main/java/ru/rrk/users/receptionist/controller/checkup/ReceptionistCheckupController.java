package ru.rrk.users.receptionist.controller.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rrk.common.dto.Receptionist;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.restClient.ReceptionistRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/checkups/{checkupId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistCheckupController {
    private final ReceptionistRestClient receptionistRestClient;
    private final CheckupRestClient checkupRestClient;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;

    @ModelAttribute("checkup")
    public CheckupPrimaryView checkup(@PathVariable("checkupId") int checkupId) {
        return this.checkupRestClient.findCheckup(checkupId).map(this.checkupPrimaryViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.checkup.not_found"));
    }

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping
    public String getCheckupInfoPage() {
        return "clinic/reception/receptionist/checkups/checkup";
    }

    @PostMapping("delete")
    public String deleteCheckup(@ModelAttribute("checkup") CheckupPrimaryView checkup,
                                @PathVariable("receptionistId") int receptionistId) {
        this.checkupRestClient.deleteCheckup(checkup.id());
        return "redirect:/clinic/reception/receptionist/%d/checkups/list".formatted(receptionistId);
    }
}
