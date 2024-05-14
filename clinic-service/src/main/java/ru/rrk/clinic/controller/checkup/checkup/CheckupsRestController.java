package ru.rrk.clinic.controller.checkup.checkup;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.checkup.checkup.payload.NewCheckupPayload;
import ru.rrk.clinic.entity.checkup.Checkup;
import ru.rrk.clinic.service.checkup.checkup.CheckupService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkups")
public class CheckupsRestController {
    private final CheckupService service;

    @GetMapping
    public Iterable<Checkup> findCheckup(Principal principal) {
        LoggerFactory.getLogger(CheckupsRestController.class).info("Principal: {}", principal);
        return this.service.findAllCheckups();
    }

    @PostMapping
    public ResponseEntity<?> createCheckup(@Valid @RequestBody NewCheckupPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Checkup checkup = this.service.createCheckup(payload.date(), payload.time(), payload.petId(), payload.vetId(), payload.checkupTypeId(), payload.checkupStateId());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("clinic-api/checkups/{checkupId}")
                            .build(Map.of("checkupId", checkup.getId())))
                    .body(checkup);
        }
    }
}
