package ru.rrk.clinic.controller.appointment.results.result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.appointment.results.result.payload.NewAppointmentResultPayload;
import ru.rrk.clinic.entity.appointment.AppointmentResult;
import ru.rrk.clinic.service.appointment.result.AppointmentResultService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments/results/{resultId:\\d+}")
public class AppointmentResultRestController {
    private final AppointmentResultService service;
    private final MessageSource messageSource;

    @ModelAttribute("appointmentResult")
    public AppointmentResult getAppointmentResult(@PathVariable("resultId") int resultId) {
        return this.service.findResult(resultId)
                .orElseThrow(() -> new NoSuchElementException("clinic.error.appointment.result.not_found"));
    }

    @GetMapping
    public AppointmentResult findAppointmentResult(@ModelAttribute("appointmentResult") AppointmentResult appointmentResult) {
        return appointmentResult;
    }

    @PatchMapping
    public ResponseEntity<?> updateAppointmentResult(@PathVariable("resultId") int resultId,
                                                     @Valid @RequestBody NewAppointmentResultPayload payload,
                                                     BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateResult(resultId, payload.currentAppointmentId(), payload.nextAppointmentId(), payload.stateId(), payload.diagnosisId(), payload.advice(), payload.prescription());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAppointmentResult(@PathVariable("resultId") int resultId) {
        this.service.deleteResult(resultId);
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
