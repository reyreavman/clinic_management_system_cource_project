package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;
import ru.rrk.common.viewModels.vet.VetView;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/checkups/{checkupId:\\d+}")
@RequiredArgsConstructor
public class VetCheckupController {
    private final VetRestClient vetRestClient;
    private final CheckupRestClient checkupRestClient;

    private final VetViewConverter vetViewConverter;
    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.vet.not_found"));
    }

    @ModelAttribute("checkup")
    public CheckupPrimaryView checkup(@PathVariable("checkupId") int checkupId) {
        return this.checkupRestClient.findCheckup(checkupId).map(this.checkupPrimaryViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.checkup.not_found"));
    }

    @GetMapping
    public String getCheckupInfoPage() {
        return "clinic/vets/checkups/checkup";
    }
}
