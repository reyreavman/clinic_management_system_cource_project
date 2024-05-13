package ru.rrk.clinic.controller.checkup.result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.checkup.result.payload.NewCheckupResultPayload;
import ru.rrk.clinic.controller.checkup.type.CheckupTypesRestController;
import ru.rrk.clinic.entity.checkup.CheckupResult;
import ru.rrk.clinic.service.checkup.result.CheckupResultService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkups/results")
public class CheckupResultsRestController {
    private final CheckupResultService service;

    @GetMapping
    public Iterable<CheckupResult> findResult(Principal principal) {
        LoggerFactory.getLogger(CheckupTypesRestController.class).info("Principal: {}", principal);
        return this.service.findAllResults();
    }

    @PostMapping
    public ResponseEntity<?> createResult(@Valid @RequestBody NewCheckupResultPayload payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            CheckupResult result = this.service.createResult(payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("clinic-api/checkups/results/{resultId}")
                            .build(Map.of("resultId", result.getId())))
                    .body(result);
        }
    }
}
