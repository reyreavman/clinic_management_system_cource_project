package ru.rrk.clinic.controller.gender;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.entity.Gender;
import ru.rrk.clinic.service.gender.GenderService;
import ru.rrk.manager.controller.genders.payload.UpdateGenderPayload;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/gender/{genderId:\\d+}")
public class GenderRestController {
    private final GenderService service;
    private final MessageSource messageSource;

    @ModelAttribute("gender")
    public Gender getGender(@PathVariable("genderId") int genderId) {
        return this.service.findGender(genderId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.gender.not_found"));
    }

    @GetMapping
    public Gender findGender(@ModelAttribute("gender") Gender gender) {
        return gender;
    }

    @PatchMapping
    public ResponseEntity<?> updateGender(@PathVariable("genderId") int genderId,
                                          @Valid @RequestBody UpdateGenderPayload payload,
                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateGender(genderId, payload.gender());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteGender(@PathVariable("genderId") int genderId) {
        this.service.deleteGender(genderId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(
                                exception.getMessage(), new Object[0],
                                exception.getMessage(), locale))));
    }
}
