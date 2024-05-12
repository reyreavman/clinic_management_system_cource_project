package ru.rrk.clinic.controller.pet.type;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.pet.type.payload.UpdatePetTypePayload;
import ru.rrk.clinic.entity.pet.PetType;
import ru.rrk.clinic.service.pet.type.PetTypeService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets/types/{typeId:\\d+}")
public class PetTypeRestController {
    private final PetTypeService service;
    private final MessageSource messageSource;

    @ModelAttribute("type")
    public PetType getType(@PathVariable("typeId") int typeId) {
        return this.service.findType(typeId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.pet.type.not_found"));
    }

    @GetMapping
    public PetType findType(@ModelAttribute("type") PetType type) {
        return type;
    }

    @PatchMapping
    public ResponseEntity<?> updateType(@PathVariable("typeId") int typeId,
                                        @Valid @RequestBody UpdatePetTypePayload payload,
                                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateType(typeId, payload.name());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteType(@PathVariable("typeId") int typeId) {
        this.service.deleteType(typeId);
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
