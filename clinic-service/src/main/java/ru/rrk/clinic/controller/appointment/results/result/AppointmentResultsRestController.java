package ru.rrk.clinic.controller.appointment.results.result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.appointment.results.result.payload.NewAppointmentResultPayload;
import ru.rrk.clinic.entity.appointment.AppointmentResult;
import ru.rrk.clinic.service.appointment.result.AppointmentResultService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments/results")
public class AppointmentResultsRestController {
    private final AppointmentResultService service;

    @GetMapping
    public Iterable<AppointmentResult> findAllResults(Principal principal) {
        LoggerFactory.getLogger(AppointmentResultsRestController.class).info("Principal: {}", principal);
        return this.service.findAllResults();
    }

    @PostMapping
    public ResponseEntity<?> createNewAppointmentResult(@Valid @RequestBody NewAppointmentResultPayload payload,
                                                        BindingResult bindingResult,
                                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            AppointmentResult appointmentResult = this.service.createResult(payload.currentAppointmentId(), payload.nextAppointmentId(), payload.stateId(), payload.diagnosisId(), payload.advice(), payload.prescription());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("clinic-api/appointments/results/{resultId}")
                            .build(Map.of("resultId", appointmentResult.getId())))
                    .body(appointmentResult);
        }
    }
}
