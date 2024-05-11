package ru.rrk.clinic.controller.label;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.label.payload.UpdateLabelPayload;
import ru.rrk.clinic.entity.Label;
import ru.rrk.clinic.service.label.LabelService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/labels/{labelId:\\d+}")
public class LabelRestController {
    private final LabelService service;
    private final MessageSource messageSource;

    @ModelAttribute("label")
    public Label getLabel(@PathVariable("labelId") int labelId) {
        return this.service.findLabel(labelId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.label.not_found"));
    }

    @GetMapping
    public Label findLabel(@ModelAttribute("label") Label label) {
        return label;
    }

    @PatchMapping
    public ResponseEntity<?> updateLabel(@PathVariable("labelId") int labelId,
                                         @Valid @RequestBody UpdateLabelPayload payload,
                                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateLabel(labelId, payload.value(), payload.date());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteLabel(@PathVariable("labelId") int labelId) {
        this.service.deleteLabel(labelId);
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
