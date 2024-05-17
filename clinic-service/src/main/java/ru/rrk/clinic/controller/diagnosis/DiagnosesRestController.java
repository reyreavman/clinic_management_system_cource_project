package ru.rrk.clinic.controller.diagnosis;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.diagnosis.payload.NewDiagnosisPayload;
import ru.rrk.clinic.entity.Diagnosis;
import ru.rrk.clinic.service.diagnosis.DiagnosisService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/diagnoses")
public class DiagnosesRestController {
    private final DiagnosisService service;

    @GetMapping
    Iterable<Diagnosis> findAllDiagnosis() {
        return this.service.findAllDiagnosis();
    }

    @PostMapping
    ResponseEntity<?> createDiagnosis(@Valid @RequestBody NewDiagnosisPayload payload,
                                      BindingResult bindingResult,
                                      UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Diagnosis diagnosis = this.service.createDiagnosis(payload.diseaseId(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/diagnoses/{diagnosisId}")
                            .build(Map.of("diagnosisId", diagnosis.getId())))
                    .body(diagnosis);
        }
    }
}
