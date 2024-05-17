package ru.rrk.manager.controller.diagnosis;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.diagnosis.payload.UpdateDiagnosisPayload;
import ru.rrk.manager.entity.Diagnosis;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.diagnosis.DiagnosisRestClient;
import ru.rrk.manager.restClients.disease.DiseaseRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/diagnoses/{diagnosisId:\\d+}")
public class DiagnosisController {
    private final DiagnosisRestClient diagnosisRestClient;
    private final DiseaseRestClient diseaseRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("diagnosis")
    public Diagnosis diagnosis(@PathVariable("diagnosisId") int diagnosisId) {
        return this.diagnosisRestClient.findDiagnosis(diagnosisId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.diagnosis.not_found"));
    }

    @GetMapping
    public String getDiagnosis() {
        return "clinic/diagnoses/diagnosis";
    }


    @GetMapping("edit")
    public String getDiagnosisEditPage(Model model) {
        model.addAttribute("diseases", this.diseaseRestClient.findAllDiseases(null));
        return "clinic/diagnoses/edit";
    }

    @PostMapping("edit")
    public String updateCheckup(@ModelAttribute(name = "diagnosis", binding = false) Diagnosis diagnosis,
                                UpdateDiagnosisPayload payload, Model model) {
        try {
            this.diagnosisRestClient.updateDiagnosis(diagnosis.id(), payload.diseaseId(), payload.description());
            return "redirect:/clinic/diagnoses/%d".formatted(diagnosis.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/diagnoses/edit";
        }
    }

    @PostMapping("delete")
    public String deleteCheckup(@ModelAttribute("diagnosis") Diagnosis diagnosis) {
        this.diagnosisRestClient.deleteDiagnosis(diagnosis.id());
        return "redirect:/clinic/diagnoses/list";
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

