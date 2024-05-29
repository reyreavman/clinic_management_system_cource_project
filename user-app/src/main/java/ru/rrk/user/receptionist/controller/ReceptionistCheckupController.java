package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.checkup.CheckupViewPrimaryConverter;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupRestClient;
import ru.rrk.user.receptionist.viewModels.checkup.CheckupViewPrimary;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/checkups/{checkupId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistCheckupController {
    private final ReceptionistRestClient receptionistRestClient;
    private final CheckupRestClient checkupRestClient;
    private final CheckupViewPrimaryConverter checkupViewPrimaryConverter;

    @ModelAttribute("checkup")
    public CheckupViewPrimary checkup(@PathVariable("checkupId") int checkupId) {
        return this.checkupRestClient.findCheckup(checkupId).map(this.checkupViewPrimaryConverter::convert)
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
}
