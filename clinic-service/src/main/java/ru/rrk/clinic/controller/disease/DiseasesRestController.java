package ru.rrk.clinic.controller.disease;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.client.ClientsRestController;
import ru.rrk.clinic.controller.disease.payload.NewDiseasePayload;
import ru.rrk.clinic.entity.Disease;
import ru.rrk.clinic.service.disease.DiseaseService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/diseases")
public class DiseasesRestController {
    private final DiseaseService service;

    @GetMapping
    public Iterable<Disease> findDiseases(@RequestParam(name = "filter", required = false) String filter,
                                          Principal principal) {
        LoggerFactory.getLogger(ClientsRestController.class).info("Principal: {}", principal);
        return this.service.findAllDiseases(filter);
    }

    @PostMapping
    public ResponseEntity<?> createDisease(@Valid @RequestBody NewDiseasePayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Disease disease = this.service.createDisease(payload.code(), payload.description());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/diseases/{diseaseId}")
                            .build(Map.of("diseaseId", disease.getId())))
                    .body(disease);
        }
    }
}
