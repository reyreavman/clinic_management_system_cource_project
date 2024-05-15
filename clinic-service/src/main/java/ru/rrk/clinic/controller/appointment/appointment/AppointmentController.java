package ru.rrk.clinic.controller.appointment.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.appointment.appointment.payload.UpdateAppointmentPayload;
import ru.rrk.clinic.entity.appointment.Appointment;
import ru.rrk.clinic.service.appointment.appointment.AppointmentService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments/{appointmentId:\\d+")
public class AppointmentController {
    private final AppointmentService service;
    private final MessageSource messageSource;

    @ModelAttribute("appointment")
    public Appointment getAppointment(@PathVariable("appointmentId") int appointmentId) {
        return this.service.findAppointment(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.appointment.not_found"));
    }

    @GetMapping
    public Appointment findAppointment(@ModelAttribute("appointment") Appointment appointment) {
        return appointment;
    }

    @PatchMapping
    public ResponseEntity<?> updateAppointment(@PathVariable("appointmentId") int appointmentId,
                                               @Valid @RequestBody UpdateAppointmentPayload payload,
                                               BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateAppointment(appointmentId, payload.petId(), payload.vetId(), payload.date(), payload.time(), payload.description(), payload.checkupId());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAppointment(@PathVariable("appointmentId") int appointmentId) {
        this.service.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale))));
    }
}
