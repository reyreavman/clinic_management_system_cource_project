package ru.rrk.clinic.controller.pet.pet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.pet.pet.payload.NewPetPayload;
import ru.rrk.clinic.entity.pet.Pet;
import ru.rrk.clinic.service.pet.pet.PetService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets")
public class PetsRestController {
    private final PetService service;

    @GetMapping
    public Iterable<Pet> findPets(@RequestParam(name = "filter", required = false) String filter) {
        return this.service.findAllPets(filter);
    }

    @PostMapping
    public ResponseEntity<?> createPet(@Valid @RequestBody NewPetPayload payload,
                                       BindingResult bindingResult,
                                       UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Pet pet = this.service.createPet(payload.name(), payload.clientId(), payload.typeId(), payload.breedId(), payload.genderId(), payload.birthday(), payload.labelId());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/pets/{petId}")
                            .build(Map.of("petId", pet.getId())))
                    .body(pet);
        }
    }
}
