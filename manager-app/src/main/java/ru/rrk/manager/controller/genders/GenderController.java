package ru.rrk.manager.controller.genders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.genders.payload.UpdateGenderPayload;
import ru.rrk.manager.entity.Gender;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.gender.GenderRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/genders/{genderId:\\d+}")
@RequiredArgsConstructor
public class GenderController {
    private final GenderRestClient restClient;

    @ModelAttribute("gender")
    public Gender gender(@PathVariable("genderId") int genderId) {
        return this.restClient.findGender(genderId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.gender.not_found"));
    }

    @GetMapping
    public String getGender() {
        return "clinic/genders/gender";
    }

    @GetMapping("edit")
    public String getGenderEditPage() {
        return "clinic/genders/edit";
    }

    @PostMapping("edit")
    public String updateGender(@ModelAttribute(name = "gender", binding = false) Gender gender,
                               UpdateGenderPayload payload, Model model) {
        try {
            this.restClient.updateGender(gender.id(), payload.gender());
            return "redirect:/clinic/genders/%d".formatted(gender.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/genders/edit";
        }
    }
}
