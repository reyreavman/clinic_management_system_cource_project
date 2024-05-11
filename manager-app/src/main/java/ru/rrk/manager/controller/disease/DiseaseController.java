package ru.rrk.manager.controller.disease;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.disease.payload.UpdateDiseasePayload;
import ru.rrk.manager.entity.Disease;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.disease.DiseaseRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/diseases/{diseaseId:\\d+}")
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("disease")
    public Disease disease(@PathVariable("diseaseId") int diseaseId) {
        return this.restClient.findDisease(diseaseId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.disease.not_found"));
    }

    @GetMapping
    public String getDisease() {
        return "clinic/diseases/disease";
    }

    @GetMapping("edit")
    public String getDiseaseEditPage() {
        return "clinic/diseases/edit";
    }

    @PostMapping("edit")
    public String updateDisease(@ModelAttribute(name = "disease", binding = false) Disease disease,
                                UpdateDiseasePayload payload, Model model) {
        try {
            this.restClient.updateDisease(disease.id(), payload.code(), payload.description());
            return "redirect:/clinic/diseases/%d".formatted(disease.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/diseases/edit";
        }
    }

    @PostMapping("delete")
    public String deleteDisease(@ModelAttribute("disease") Disease disease) {
        this.restClient.deleteDisease(disease.id());
        return "redirect:/clinic/diseases/list";
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
