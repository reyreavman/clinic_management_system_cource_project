package ru.rrk.clinic.controller.speciality;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.speciality.payload.UpdateSpecialityPayload;
import ru.rrk.clinic.entity.Speciality;
import ru.rrk.clinic.services.speciality.SpecialityService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/specialities/{specialityId:\\d+}")
public class SpecialityRestController {
    private final SpecialityService service;
    private final MessageSource messageSource;

    @ModelAttribute("speciality")
    public Speciality getSpeciality(@PathVariable("specialityId") int specialityId) {
        return this.service.findSpeciality(specialityId).orElseThrow(() -> new NoSuchElementException("clinic.errors.speciality.not_found"));
    }

    @GetMapping
    public Speciality findSpeciality(@ModelAttribute("speciality") Speciality speciality) {
        return speciality;
    }

    @PatchMapping
    public ResponseEntity<?> updateSpeciality(@PathVariable("specialityId") int specialityId, @Valid @RequestBody UpdateSpecialityPayload payload, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateSpeciality(specialityId, payload.name());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSpeciality(@PathVariable("specialityId") int specialityId) {
        this.service.deleteSpeciality(specialityId);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale))));
    }
}
