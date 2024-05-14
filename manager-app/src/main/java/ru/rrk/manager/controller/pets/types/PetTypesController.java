package ru.rrk.manager.controller.pets.types;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rrk.manager.controller.pets.types.payload.NewPetTypePayload;
import ru.rrk.manager.entity.pet.PetType;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.pet.type.PetTypeRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/pets/types")
public class PetTypesController {
    private final PetTypeRestClient restClient;

    @GetMapping("list")
    public String getTypesList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("types", this.restClient.findAllTypes(filter));
        model.addAttribute("filter", filter);
        return "clinic/pets/types/list";
    }

    @GetMapping("create")
    public String getNewTypePage() {
        return "clinic/pets/types/new_type";
    }

    @PostMapping("create")
    public String createType(NewPetTypePayload payload, Model model) {
        try {
            PetType type = this.restClient.createType(payload.name());
            return "redirect:/clinic/pets/types/%d".formatted(type.id());
        }  catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/pets/types/new_type";
        }
    }

}
