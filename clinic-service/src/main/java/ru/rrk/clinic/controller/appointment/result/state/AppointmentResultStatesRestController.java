package ru.rrk.clinic.controller.appointment.result.state;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.appointment.result.state.payload.NewAppointmentResultStatePayload;
import ru.rrk.clinic.entity.appointment.AppointmentResultState;
import ru.rrk.clinic.service.appointment.result.state.AppointmentResultStateService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments/results/states")
public class AppointmentResultStatesRestController {
    private final AppointmentResultStateService service;

    @GetMapping
    public Iterable<AppointmentResultState> findAllResults(Principal principal) {
        LoggerFactory.getLogger(AppointmentResultStatesRestController.class).info("Principal: {}", principal);
        return this.service.findAllStates();
    }

    @PostMapping
    public ResponseEntity<?> createState(@Valid @RequestBody NewAppointmentResultStatePayload payload,
                                                              BindingResult bindingResult,
                                                              UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            AppointmentResultState state = this.service.createState(payload.state());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("clinic-api/appointments/results/states/{stateId}")
                            .build(Map.of("stateId", state.getId())))
                    .body(state);
        }
    }
}
