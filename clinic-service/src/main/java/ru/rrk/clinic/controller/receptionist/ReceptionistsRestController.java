package ru.rrk.clinic.controller.receptionist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.receptionist.payload.NewReceptionistPayload;
import ru.rrk.clinic.entity.Receptionist;
import ru.rrk.clinic.service.receptionist.ReceptionistService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/receptionists")
public class ReceptionistsRestController {
    private final ReceptionistService service;

    @GetMapping
    public Iterable<Receptionist> findReceptionists(@RequestParam(name = "filter", required = false) String filter,
                                                    Principal principal) {
        LoggerFactory.getLogger(ReceptionistRestController.class).info("Principal: {}", principal);
        return this.service.findAllReceptionists(filter);
    }

    @PostMapping
    public ResponseEntity<?> createReceptionist(@Valid @RequestBody NewReceptionistPayload payload,
                                                BindingResult bindingResult,
                                                UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Receptionist receptionist = this.service.createReceptionist(payload.firstName(), payload.lastName());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/receptionists/{receptionistId}")
                            .build(Map.of("receptionistId", receptionist.getId())))
                    .body(receptionist);
        }
    }
}
