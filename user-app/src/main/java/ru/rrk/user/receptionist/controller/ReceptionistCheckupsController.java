package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.user.receptionist.controller.payload.NewCheckupPayload;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.dto.checkup.Checkup;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPayloadNormalizer;
import ru.rrk.user.receptionist.mapper.checkup.CheckupViewPrimaryConverter;
import ru.rrk.user.receptionist.mapper.PetViewSummaryConverter;
import ru.rrk.user.receptionist.mapper.VetViewSummaryConverter;
import ru.rrk.user.receptionist.restClient.*;
import ru.rrk.user.receptionist.restClient.checkup.CheckupRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupTypeRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/checkups")
@RequiredArgsConstructor
public class ReceptionistCheckupsController {
    private final ReceptionistRestClient receptionistRestClient;
    private final PetRestClient petRestClient;
    private final VetRestClient vetRestClient;
    private final CheckupTypeRestClient checkupTypeRestClient;
    private final CheckupRestClient checkupRestClient;

    private final CheckupViewPrimaryConverter checkupViewPrimaryConverter;
    private final PetViewSummaryConverter petViewSummaryConverter;
    private final VetViewSummaryConverter vetViewSummaryConverter;

    private final CheckupPayloadNormalizer checkupPayloadNormalizer;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping("create")
    public String getNewCheckupPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null).stream().map(this.petViewSummaryConverter::convert).toList());
        model.addAttribute("vets", this.vetRestClient.findAllVets(null).stream().map(this.vetViewSummaryConverter::convert).toList());
        model.addAttribute("types", this.checkupTypeRestClient.findAllTypes().stream().toList());
        return "clinic/reception/receptionist/checkups/create";
    }

    @PostMapping("create")
    public String createNewCheckup(NewCheckupPayload payload, Model model) {
        try {
            payload = this.checkupPayloadNormalizer.normalizePayload(payload);
            Checkup checkup = this.checkupRestClient.createCheckup(payload.getDate(), payload.getTime(), payload.getPetId(), payload.getVetId(), payload.getTypeId(), payload.getStateId(), payload.getResultId());
            return "redirect:/clinic/reception/receptionist/{receptionistId:\\d+}/checkups/%d".formatted(checkup);
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "clinic/reception/receptionist/checkups/create";
        }
    }

    @GetMapping("list")
    public String getCheckupsListPage(Model model) {
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream().map(this.checkupViewPrimaryConverter::convert).toList());
        return "clinic/reception/receptionist/checkups/list";
    }
}
