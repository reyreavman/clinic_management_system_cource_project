package ru.rrk.clinic.controller.receptionist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.receptionist.payload.UpdateReceptionistPayload;
import ru.rrk.clinic.entity.Receptionist;
import ru.rrk.clinic.service.receptionist.ReceptionistService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/receptionists/{receptionistId:\\d+}")
public class ReceptionistRestController {
    private final ReceptionistService service;
    private final MessageSource messageSource;

    @ModelAttribute("receptionist")
    public Receptionist getReceptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.service.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.receptionist.not_found"));
    }

    @GetMapping
    public Receptionist findReceptionist(@ModelAttribute("receptionist") Receptionist receptionist) {
        return receptionist;
    }

    @PatchMapping
    public ResponseEntity<?> updateReceptionist(@PathVariable("receptionistId") int receptionistId,
                                                @Valid @RequestBody UpdateReceptionistPayload payload,
                                                BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateReceptionist(receptionistId, payload.firstName(), payload.lastName());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReceptionist(@PathVariable("receptionistId") int receptionistId) {
        this.service.deleteReceptionist(receptionistId);
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
