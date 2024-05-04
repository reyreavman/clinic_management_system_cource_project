package ru.rrk.clinic.controller.speciality;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.speciality.payload.NewSpecialityPayload;
import ru.rrk.clinic.entity.Speciality;
import ru.rrk.clinic.services.speciality.SpecialityService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/specialities")
public class SpecialitiesRestController {
    private final SpecialityService service;

    @GetMapping
    public Iterable<Speciality> findSpeciality(@RequestParam(name = "filter", required = false) String filter,
                                               Principal principal) {
        LoggerFactory.getLogger(SpecialitiesRestController.class).info("Principal: {}", principal);
        return this.service.findAllSpecialities(filter);
    }

    @PostMapping
    public ResponseEntity<?> createSpeciality(@Valid @RequestBody NewSpecialityPayload payload,
                                              BindingResult bindingResult,
                                              UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Speciality speciality = this.service.createSpeciality(payload.name());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/specialities/{specialityId}")
                            .build(Map.of("specialityId", speciality.getId())))
                    .body(speciality);
        }
    }
}