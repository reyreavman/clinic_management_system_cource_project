package ru.rrk.manager.controller.diagnosis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.diagnosis.payload.NewDiagnosisPayload;
import ru.rrk.manager.entity.Diagnosis;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.diagnosis.DiagnosisRestClient;
import ru.rrk.manager.restClients.disease.DiseaseRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/diagnoses")
public class DiagnosesController {
    private final DiagnosisRestClient diagnosisRestClient;
    private final DiseaseRestClient diseaseRestClient;

    @GetMapping("list")
    public String getDiagnosesList(Model model) {
        model.addAttribute("diagnoses", this.diagnosisRestClient.findAllDiagnosis());
        return "clinic/diagnoses/list";
    }

    @GetMapping("create")
    public String getNewDiagnosisPage(Model model) {
        model.addAttribute("diseases", this.diseaseRestClient.findAllDiseases(null));
        return "clinic/diagnoses/new_diagnosis";
    }

    public String createDiagnosis(NewDiagnosisPayload payload, Model model) {
        try {
            Diagnosis diagnosis = this.diagnosisRestClient.createDiagnosis(payload.diseaseId(), payload.description());
            return "redirect:/clinic/diagnoses/%d".formatted(diagnosis.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/diagnoses/new_diagnosis";
        }
    }
}
