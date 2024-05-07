package ru.rrk.clinic.controller.gender;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.gender.payload.NewGenderPayload;
import ru.rrk.clinic.entity.Gender;
import ru.rrk.clinic.service.gender.GenderService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/genders")
public class GendersRestController {
    private final GenderService service;

    @GetMapping
    public Iterable<Gender> findGenders(Principal principal) {
        LoggerFactory.getLogger(GendersRestController.class).info("Principal: {}", principal);
        return this.service.findAllGenders();
    }

    @PostMapping
    public ResponseEntity<?> createGender(@Valid @RequestBody NewGenderPayload payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Gender gender = this.service.createGender(payload.gender());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/genders/{genderId}")
                            .build(Map.of("genderId", gender.getId())))
                    .body(gender);
        }
    }
}
