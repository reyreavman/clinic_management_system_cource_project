package ru.rrk.user.receptionist.controller.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.user.receptionist.controller.BadRequestException;
import ru.rrk.user.receptionist.controller.checkup.payload.NewCheckupDetailsPayload;
import ru.rrk.user.receptionist.controller.checkup.payload.NewCheckupSummaryPayload;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.dto.checkup.Checkup;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPayloadNormalizer;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.user.receptionist.mapper.pet.PetViewSummaryConverter;
import ru.rrk.user.receptionist.mapper.vet.VetSummaryViewConverter;
import ru.rrk.user.receptionist.restClient.PetRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;
import ru.rrk.user.receptionist.restClient.VetRestClient;
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

    private final CheckupPrimaryViewConverter checkupPrimaryViewConverter;
    private final PetViewSummaryConverter petViewSummaryConverter;
    private final VetSummaryViewConverter vetSummaryViewConverter;

    private final CheckupPayloadNormalizer checkupPayloadNormalizer;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @GetMapping("create")
    public String getNewCheckupPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null).stream().map(this.petViewSummaryConverter::convert).toList());
        model.addAttribute("vets", this.vetRestClient.findAllVets(null).stream().map(this.vetSummaryViewConverter::convert).toList());
        model.addAttribute("types", this.checkupTypeRestClient.findAllTypes().stream().toList());
        return "clinic/reception/receptionist/checkups/create";
    }

    @PostMapping("create")
    public String createNewCheckup(NewCheckupSummaryPayload payload, Model model) {
        try {
            NewCheckupDetailsPayload payloadDetails = this.checkupPayloadNormalizer.normalizePayload(payload);
            Checkup checkup = this.checkupRestClient.createCheckup(
                    payloadDetails.date(),
                    payloadDetails.time(),
                    payloadDetails.petId(),
                    payloadDetails.vetId(),
                    payloadDetails.checkupTypeId(),
                    payloadDetails.checkupStateId(),
                    payloadDetails.checkupResultId());
            return "redirect:/clinic/reception/receptionist/{receptionistId}/checkups/%d".formatted(checkup.id());
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "clinic/reception/receptionist/checkups/create";
        }
    }

    @GetMapping("list")
    public String getCheckupsListPage(Model model) {
//        , HttpServletRequest request in arguments
//        System.out.println(request.getHeader("referer") + " - triggered");
        model.addAttribute("checkups", this.checkupRestClient.findAllCheckups().stream().map(this.checkupPrimaryViewConverter::convert).toList());
        return "clinic/reception/receptionist/checkups/list";
    }
}
