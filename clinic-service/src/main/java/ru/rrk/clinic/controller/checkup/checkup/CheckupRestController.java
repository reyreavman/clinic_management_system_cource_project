package ru.rrk.clinic.controller.checkup.checkup;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.checkup.checkup.payload.UpdateCheckupPayload;
import ru.rrk.clinic.entity.checkup.Checkup;
import ru.rrk.clinic.service.checkup.checkup.CheckupService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkups/{checkupId:\\d+}")
public class CheckupRestController {
    private final CheckupService service;
    private final MessageSource messageSource;

    @ModelAttribute("checkup")
    public Checkup getCheckup(@PathVariable("checkupId") int checkupId) {
        return this.service.findCheckup(checkupId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.not_found"));
    }

    @GetMapping
    public Checkup findCheckup(@ModelAttribute("checkup") Checkup checkup) {
        return checkup;
    }

    @PatchMapping
    public ResponseEntity<?> updateCheckup(@PathVariable("checkupId") int checkupId,
                                           @Valid @RequestBody UpdateCheckupPayload payload,
                                           BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateCheckup(checkupId, payload.date(), payload.time(), payload.petId(), payload.vetId(), payload.checkupTypeId(), payload.checkupStateId(), payload.checkupResultId());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVet(@PathVariable("checkupId") int checkupId) {
        this.service.deleteCheckup(checkupId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale))));
    }
}
