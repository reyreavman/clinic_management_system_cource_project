package ru.rrk.manager.controller.labels;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.labels.payload.NewLabelPayload;
import ru.rrk.manager.entity.Label;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.label.LabelRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/labels")
public class LabelsController {
    private final LabelRestClient restClient;

    @GetMapping("list")
    public String getLabelsList(Model model) {
        model.addAttribute("labels", this.restClient.findAllLabels());
        return "clinic/labels/list";
    }

    @GetMapping("create")
    public String getNewLabelPage() {
        return "clinic/labels/new_label";
    }

    @PostMapping("create")
    public String createLabel(NewLabelPayload payload, Model model) {
        try {
            Label label = this.restClient.createLabel(payload.value(), payload.date());
            return "redirect:/clinic/labels/%d".formatted(label.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/labels/new_label";
        }
    }

}
