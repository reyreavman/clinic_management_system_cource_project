package ru.rrk.manager.controller.pet.labels;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.pet.labels.payload.UpdateLabelPayload;
import ru.rrk.manager.entity.pet.Label;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.pet.label.LabelRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/pets/labels/{labelId:\\d+}")
@RequiredArgsConstructor
public class LabelController {
    private final LabelRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("label")
    public Label label(@PathVariable("labelId") int labelId) {
        return this.restClient.findLabel(labelId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.label.not_found"));
    }

    @GetMapping
    public String getLabel() {
        return "clinic/pets/labels/label";
    }

    @GetMapping("edit")
    public String getLabelEditPage() {
        return "clinic/pets/labels/edit";
    }

    @PostMapping("edit")
    public String updateLabel(@ModelAttribute(name = "label", binding = false) Label label,
                              UpdateLabelPayload payload, Model model) {
        try {
            this.restClient.updateLabel(label.id(), payload.value(), payload.date());
            return "redirect:/clinic/pets/labels/%d".formatted(label.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/pets/labels/edit";
        }
    }

    @PostMapping("delete")
    public String deleteLabel(@ModelAttribute("label") Label label) {
        this.restClient.deleteLabel(label.id());
        return "redirect:/clinic/pets/labels/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
