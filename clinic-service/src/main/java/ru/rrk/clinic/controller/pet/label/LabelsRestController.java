package ru.rrk.clinic.controller.pet.label;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.pet.label.payload.NewLabelPayload;
import ru.rrk.clinic.entity.pet.Label;
import ru.rrk.clinic.service.pet.label.LabelService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets/labels")
public class LabelsRestController {
    private final LabelService service;

    @GetMapping
    public Iterable<Label> findLabels(Principal principal) {
        LoggerFactory.getLogger(LabelsRestController.class).info("Principal: {}", principal);
        return this.service.findAllLabels();
    }

    @PostMapping
    public ResponseEntity<?> createLabel(@Valid @RequestBody NewLabelPayload payload,
                                         BindingResult bindingResult,
                                         UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Label label = this.service.createLabel(payload.value(), payload.date());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/pets/labels/{labelId}")
                            .build(Map.of("labelId", label.getId())))
                    .body(label);
        }
    }
}
