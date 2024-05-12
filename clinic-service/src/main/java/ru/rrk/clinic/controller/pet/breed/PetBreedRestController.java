package ru.rrk.clinic.controller.pet.breed;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.pet.breed.payload.UpdatePetBreedPayload;
import ru.rrk.clinic.entity.pet.PetBreed;
import ru.rrk.clinic.service.pet.breed.PetBreedService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets/breeds/{breedId:\\d+}")
public class PetBreedRestController {
    private final PetBreedService service;
    private final MessageSource messageSource;

    @ModelAttribute("breed")
    public PetBreed getBreed(@PathVariable("breedId") int breedId) {
        return this.service.findBreed(breedId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.pet.breed.not_found"));
    }

    @GetMapping
    public PetBreed findBreed(@ModelAttribute("breed") PetBreed breed) {
        return breed;
    }

    @PatchMapping
    public ResponseEntity<?> updateBreed(@PathVariable("breedId") int breedId,
                                         @Valid @RequestBody UpdatePetBreedPayload payload,
                                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateBreed(breedId, payload.name(), payload.typeId());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBreed(@PathVariable("breedId") int breedId) {
        this.service.deleteBreed(breedId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale))));
    }
}