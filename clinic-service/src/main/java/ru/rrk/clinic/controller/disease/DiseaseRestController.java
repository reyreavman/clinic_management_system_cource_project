package ru.rrk.clinic.controller.disease;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.disease.payload.UpdateDiseasePayload;
import ru.rrk.clinic.entity.Disease;
import ru.rrk.clinic.service.disease.DiseaseService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/diagnoses/diseases/{diseaseId:\\d+}")
public class DiseaseRestController {
    private final DiseaseService service;
    private final MessageSource messageSource;

    @ModelAttribute("disease")
    public Disease getDisease(@PathVariable("diseaseId") int diseaseId) {
        return this.service.findDisease(diseaseId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.diagnoses.disease.not_found"));
    }

    @GetMapping
    public Disease findDisease(@ModelAttribute("disease") Disease disease) {
        return disease;
    }

    @PatchMapping
    public ResponseEntity<?> updateDisease(@PathVariable("diseaseId") int diseaseId,
                                           @Valid @RequestBody UpdateDiseasePayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateDisease(diseaseId, payload.code(), payload.description());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDisease(@PathVariable("diseaseId") int diseaseId) {
        this.service.deleteDisease(diseaseId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(
                                exception.getMessage(), new Object[0],
                                exception.getMessage(), locale))));
    }
}
