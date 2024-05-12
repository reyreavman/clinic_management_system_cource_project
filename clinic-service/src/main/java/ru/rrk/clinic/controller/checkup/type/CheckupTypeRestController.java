package ru.rrk.clinic.controller.checkup.type;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.checkup.type.payload.UpdateCheckupTypePayload;
import ru.rrk.clinic.entity.checkup.CheckupType;
import ru.rrk.clinic.service.checkup.type.CheckupTypeService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkups/types/{typeId:\\d+}")
public class CheckupTypeRestController {
    private final CheckupTypeService service;
    private final MessageSource messageSource;

    @ModelAttribute("type")
    public CheckupType getType(@PathVariable("typeId") int typeId) {
        return this.service.findType(typeId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.type.not_found"));
    }

    @GetMapping
    public CheckupType findType(@ModelAttribute("type") CheckupType type) {
        return type;
    }

    @PatchMapping
    public ResponseEntity<?> updateType(@PathVariable("typeId") int typeId,
                                        @Valid @RequestBody UpdateCheckupTypePayload payload,
                                        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateType(typeId, payload.type());
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
