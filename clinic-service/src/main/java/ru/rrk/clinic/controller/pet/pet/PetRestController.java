package ru.rrk.clinic.controller.pet.pet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.pet.pet.payload.UpdatePetPayload;
import ru.rrk.clinic.entity.pet.Pet;
import ru.rrk.clinic.service.pet.pet.PetService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets/{petId:\\d+}")
public class PetRestController {
    private final PetService service;
    private final MessageSource messageSource;

    @ModelAttribute("pet")
    public Pet getPet(@PathVariable("petId") int petId) {
        return this.service.findPet(petId)
                .orElseThrow(() -> new NoSuchElementException("clinic.error.pet.not_found"));
    }

    @GetMapping
    public Pet findPet(@ModelAttribute("pet") Pet pet) {
        return pet;
    }

    @PatchMapping
    public ResponseEntity<?> updatePet(@PathVariable("petId") int petId,
                                       @Valid @RequestBody UpdatePetPayload payload,
                                       BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updatePet(petId, payload.name(), payload.clientId(), payload.typeId(), payload.breedId(), payload.genderId(), payload.birthday(), payload.labelId());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePet(@PathVariable("petId") int petId) {
        this.service.deletePet(petId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale))));
    }
}