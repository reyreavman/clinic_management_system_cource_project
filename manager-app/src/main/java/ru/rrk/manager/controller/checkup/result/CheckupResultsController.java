package ru.rrk.manager.controller.checkup.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.checkup.result.payload.NewCheckupResultPayload;
import ru.rrk.manager.entity.checkup.CheckupResult;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.result.CheckupResultRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/checkups/results")
public class CheckupResultsController {
    private final CheckupResultRestClient restClient;

    @GetMapping("list")
    public String getResultsList(Model model) {
        model.addAttribute("results", this.restClient.findAllResults());
        return "clinic/checkups/results/list";
    }

    @GetMapping("create")
    public String getNewResultPage() {
        return "clinic/checkups/results/new_result";
    }

    @PostMapping("create")
    public String createResult(NewCheckupResultPayload payload, Model model) {
        try {
            CheckupResult result = this.restClient.createResult(payload.description());
            return "redirect:/clinic/checkups/results/%d".formatted(result.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/results/new_result";
        }
    }
}
