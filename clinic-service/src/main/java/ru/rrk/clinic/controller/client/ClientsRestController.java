package ru.rrk.clinic.controller.client;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.client.payload.Client.NewClientPayload;
import ru.rrk.clinic.entity.Client;
import ru.rrk.clinic.services.client.ClientService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/clients")
public class ClientsRestController {
    private final ClientService service;

    @GetMapping
    public Iterable<Client> findClients(@RequestParam(name = "filter", required = false) String filter,
                                        Principal principal) {
        LoggerFactory.getLogger(ClientsRestController.class).info("Principal: {}", principal);
        return this.service.findAllClients(filter);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody NewClientPayload payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Client client = this.service.createClient(payload.firstName(), payload.lastName(), payload.phoneNumber(), payload.email());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/clients/{clientId}")
                            .build(Map.of("clientId", client.getId())))
                    .body(client);
        }
    }
}
