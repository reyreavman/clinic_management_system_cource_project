package ru.rrk.clinic.controller.vet.vet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.vet.vet.payload.NewVetPayload;
import ru.rrk.clinic.entity.vet.Vet;
import ru.rrk.clinic.service.vet.vet.VetService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/vets")
public class VetsRestController {
    private final VetService service;

    @GetMapping
    public Iterable<Vet> findVets(@RequestParam(name = "filter", required = false) String filter) {
        return this.service.findAllVets(filter);
    }

    @PostMapping
    public ResponseEntity<?> createVet(@Valid @RequestBody NewVetPayload payload,
                                       BindingResult bindingResult,
                                       UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Vet vet = this.service.createVet(payload.firstName(), payload.lastName(), payload.speciality_id());
            return ResponseEntity
                    .created(uriComponentsBuilder.replacePath("/clinic-api/vets/{vetId}").build(Map.of("vetId", vet.getId())))
                    .body(vet);
        }
    }
}
