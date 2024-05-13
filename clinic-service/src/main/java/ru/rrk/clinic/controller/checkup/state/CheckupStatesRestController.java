package ru.rrk.clinic.controller.checkup.state;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.checkup.state.payload.NewCheckupStatePayload;
import ru.rrk.clinic.controller.checkup.type.CheckupTypesRestController;
import ru.rrk.clinic.entity.checkup.CheckupState;
import ru.rrk.clinic.service.checkup.state.CheckupStateService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkups/states")
public class CheckupStatesRestController {
    private final CheckupStateService service;

    @GetMapping
    public Iterable<CheckupState> findStates(Principal principal) {
        LoggerFactory.getLogger(CheckupTypesRestController.class).info("Principal: {}", principal);
        return this.service.findAllStates();
    }

    @PostMapping
    public ResponseEntity<?> createState(@Valid @RequestBody NewCheckupStatePayload payload,
                                         BindingResult bindingResult,
                                         UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            CheckupState state = this.service.createState(payload.type());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("clinic-api/checkups/states/{stateId}")
                            .build(Map.of("stateId", state.getId())))
                    .body(state);
        }
    }
}
