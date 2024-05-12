package ru.rrk.clinic.controller.pet.type;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.pet.type.payload.NewPetTypePayload;
import ru.rrk.clinic.entity.pet.PetType;
import ru.rrk.clinic.service.pet.type.PetTypeService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/pets/types")
public class PetTypesRestController {
    private final PetTypeService service;

    @GetMapping
    public Iterable<PetType> findTypes(@RequestParam(name = "filter", required = false) String filter,
                                       Principal principal) {
        LoggerFactory.getLogger(PetTypesRestController.class).info("Principal: {}", principal);
        return this.service.findAllTypes(filter);
    }

    @PostMapping
    public ResponseEntity<?> createType(@Valid @RequestBody NewPetTypePayload payload,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            PetType type = this.service.createType(payload.name());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/pets.types/{typeId}")
                            .build(Map.of("typeId", type.getId())))
                    .body(type);
        }
    }
}
