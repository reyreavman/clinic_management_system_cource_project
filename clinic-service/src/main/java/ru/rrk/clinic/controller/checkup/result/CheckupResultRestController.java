package ru.rrk.clinic.controller.checkup.result;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.checkup.result.payload.UpdateCheckupResultPayload;
import ru.rrk.clinic.entity.checkup.CheckupResult;
import ru.rrk.clinic.service.checkup.result.CheckupResultService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkups/results/{resultId:\\d+}")
public class CheckupResultRestController {
    private final CheckupResultService service;
    private final MessageSource messageSource;

    @ModelAttribute("result")
    public CheckupResult getResult(@PathVariable("resultId") int resultId) {
        return this.service.findResult(resultId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.result.not_found"));

    }

    @GetMapping
    public CheckupResult findResult(@ModelAttribute("result") CheckupResult result) {
        return result;
    }

    @PatchMapping
    public ResponseEntity<?> updateResult(@PathVariable("resultId") int resultId,
                                          @Valid @RequestBody UpdateCheckupResultPayload payload,
                                          BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateResult(resultId, payload.description());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteResult(@PathVariable("resultId") int resultId) {
        this.service.deleteResult(resultId);
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
