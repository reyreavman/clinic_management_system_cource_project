package ru.rrk.clinic.controller.pet.breed;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.pet.breed.payload.NewPetBreedPayload;
import ru.rrk.clinic.entity.pet.PetBreed;
import ru.rrk.clinic.service.pet.breed.PetBreedService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets/breeds")
public class PetBreedsRestController {
    private final PetBreedService service;

    @GetMapping
    public Iterable<PetBreed> findBreeds(@RequestParam(name = "filter", required = false) String filter) {
        return this.service.findAllBreeds(filter);
    }

    @PostMapping
    public ResponseEntity<?> createBreed(@Valid @RequestBody NewPetBreedPayload payload,
                                         BindingResult bindingResult,
                                         UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            PetBreed breed = this.service.createBreed(payload.name(), payload.typeId());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/pets/breeds/{breedId}")
                            .build(Map.of("breedId", breed.getId())))
                    .body(breed);
        }
    }
}
