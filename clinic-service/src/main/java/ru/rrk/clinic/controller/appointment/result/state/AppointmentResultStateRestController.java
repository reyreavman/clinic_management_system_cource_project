package ru.rrk.clinic.controller.appointment.result.state;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.appointment.result.state.payload.UpdateAppointmentResultStatePayload;
import ru.rrk.clinic.entity.appointment.AppointmentResultState;
import ru.rrk.clinic.service.appointment.result.AppointmentResultStateService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments/results/states/{stateId:\\d+}")
public class AppointmentResultStateRestController {
    private final AppointmentResultStateService service;
    private final MessageSource messageSource;

    @ModelAttribute("state")
    public AppointmentResultState getState(@PathVariable("stateId") int stateId) {
        return this.service.findState(stateId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.appointment.result.state.not_found"));
    }

    @GetMapping
    public AppointmentResultState findState(@ModelAttribute("state") AppointmentResultState state) {
        return state;
    }

    @PatchMapping
    public ResponseEntity<?> updateState(@PathVariable("stateId") int stateId,
                                         @Valid @RequestBody UpdateAppointmentResultStatePayload payload,
                                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateState(stateId, payload.state());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteState(@PathVariable("stateId") int stateId) {
        this.service.deleteState(stateId);
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
