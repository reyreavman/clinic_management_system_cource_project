package ru.rrk.clinic.controller.checkup.type;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.checkup.type.payload.NewCheckupTypePayload;
import ru.rrk.clinic.entity.checkup.CheckupType;
import ru.rrk.clinic.service.checkup.type.CheckupTypeService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/checkuptypes/")
public class CheckupTypesRestController {
    private final CheckupTypeService service;

    @GetMapping
    public Iterable<CheckupType> findTypes(Principal principal) {
        LoggerFactory.getLogger(CheckupTypesRestController.class).info("Principal: {}", principal);
        return this.service.findAllTypes();
    }

    @PostMapping
    public ResponseEntity<?> createType(@Valid @RequestBody NewCheckupTypePayload payload,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            CheckupType type = this.service.createType(payload.type());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/clinic-api/checkuptype/{checkuptypeId}")
                            .build(Map.of("checkuptypeId", type.getId())))
                    .body(type);
        }
    }
}
