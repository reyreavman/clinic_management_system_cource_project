package ru.rrk.clinic.controller.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rrk.clinic.controller.client.payload.UpdateClientPayload;
import ru.rrk.clinic.entity.Client;
import ru.rrk.clinic.services.client.ClientService;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/clients/{clientId:\\d+}")
public class ClientRestController {
    private final ClientService service;
    private final MessageSource messageSource;

    @ModelAttribute("client")
    public Client getClient(@PathVariable("clientId") int clientId) {
        return this.service.findClient(clientId).orElseThrow(() -> new NoSuchElementException("clinic.errors.client.not_found"));
    }

    @GetMapping
    public Client findClient(@ModelAttribute("client") Client client) {
        return client;
    }

    @PatchMapping
    public ResponseEntity<?> updateClient(@PathVariable("clientId") int clientId, @Valid @RequestBody UpdateClientPayload payload, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            this.service.updateClient(clientId, payload.firstName(), payload.lastName(), payload.phoneNumber(), payload.email());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClient(@PathVariable("clientId") int clientId) {
        this.service.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(exception.getMessage(),
                                new Object[0], exception.getMessage(), locale))));
    }
}
