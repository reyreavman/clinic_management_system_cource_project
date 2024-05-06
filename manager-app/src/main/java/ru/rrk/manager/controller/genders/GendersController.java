package ru.rrk.manager.controller.genders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.controller.genders.payload.NewGenderPayload;
import ru.rrk.manager.entity.Gender;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.gender.GenderRestClient;

@Controller
@RequestMapping("clinic/genders")
@RequiredArgsConstructor
public class GendersController {
    private GenderRestClient restClient;

    @GetMapping("list")
    public String getGendersList(Model model) {
        model.addAttribute("genders", this.restClient.findAllGenders());
        return "clinic/genders/list";
    }

    @GetMapping("create")
    public String getNewGenderPage() {
        return "clinic/genders/new_gender";
    }

    @PostMapping("create")
    public String createGender(NewGenderPayload payload, Model model) {
        try {
            Gender gender = this.restClient.createGender(payload.gender());
            return "redirect:/clinic/genders/%d".formatted(gender.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/genders/new_gender";
        }
    }
}
