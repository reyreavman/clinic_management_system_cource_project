package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.restClient.checkup.CheckupRestClient;
import ru.rrk.common.restClient.checkup.CheckupResultRestClient;
import ru.rrk.common.viewModels.vet.VetView;
import ru.rrk.users.receptionist.controller.BadRequestException;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/checkups/{checkupId:\\d+}/result")
@RequiredArgsConstructor
public class VetCheckupResultController {
    private final VetRestClient vetRestClient;
    private final CheckupRestClient checkupRestClient;
    private final CheckupResultRestClient checkupResultRestClient;

    private final VetViewConverter vetViewConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.vet.not_found"));
    }

    @ModelAttribute("checkupId")
    public int checkup(@PathVariable("checkupId") int checkupId) {
        return checkupId;
    }

    @GetMapping("create")
    public String getNewResultPage() {
        return "clinic/vets/checkups/results/create";
    }

    @PostMapping("create")
    public String createNewResult(UpdateCheckupResultPayload payload, Model model,
                                  @PathVariable("checkupId") int checkupId,
                                  @PathVariable("vetId") int vetId) {
        try {
            int resultId = this.checkupRestClient.findCheckup(checkupId).orElseThrow(NoSuchElementException::new).checkupResult().id();
            this.checkupResultRestClient.updateResult(resultId, payload.description());
            return "redirect:/clinic/vets/vet/%d/checkups/%d".formatted(vetId, checkupId);
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "clinic/vets/checkups/results/create";
        }
    }
}
