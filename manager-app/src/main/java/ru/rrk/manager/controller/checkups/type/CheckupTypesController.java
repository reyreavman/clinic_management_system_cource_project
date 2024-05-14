package ru.rrk.manager.controller.checkups.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.checkups.type.payload.NewCheckupTypePayload;
import ru.rrk.manager.entity.checkup.CheckupType;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.type.CheckupTypeRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/checkups/types")
public class CheckupTypesController {
    private final CheckupTypeRestClient restClient;

    @GetMapping("list")
    public String getTypesList(Model model) {
        model.addAttribute("types", this.restClient.findAllTypes());
        return "clinic/checkups/types/list";
    }

    @GetMapping("create")
    public String getNewTypePage() {
        return "clinic/checkups/types/new_type";
    }

    @PostMapping("create")
    public String createType(NewCheckupTypePayload payload, Model model) {
        try {
            CheckupType type = this.restClient.createType(payload.type());
            return "redirect:/clinic/checkups/types/%d".formatted(type.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/types/new_type";
        }
    }
}
