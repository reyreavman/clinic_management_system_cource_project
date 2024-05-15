package ru.rrk.clinic.controller.vet.vet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.vet.vet.payload.UpdateVetPayload;
import ru.rrk.clinic.entity.vet.Vet;
import ru.rrk.clinic.service.vet.vet.VetService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/vets/{vetId:\\d+}")
public class VetRestController {
    private final VetService service;
    private final MessageSource messageSource;

    @ModelAttribute("vet")
    public Vet getVet(@PathVariable("vetId") int vetId) {
        return this.service.findVet(vetId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vet.not_found"));
    }

    @GetMapping
    public Vet findVet(@ModelAttribute("vet") Vet vet) {
        return vet;
    }

    @PatchMapping
    public ResponseEntity<?> updateVet(@PathVariable("vetId") int vetId,
                                       @Valid @RequestBody UpdateVetPayload payload,
                                       BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateVet(vetId, payload.firstName(), payload.lastName(), payload.specialityId());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVet(@PathVariable("vetId") int vetId) {
        this.service.deleteVet(vetId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale))));
    }
}