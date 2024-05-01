package ru.rrk.manager.controller.specialities;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rrk.manager.controller.specialities.payload.NewSpecialityPayload;
import ru.rrk.manager.entity.Speciality;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.speciality.SpecialityRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/specialities")
public class SpecialitiesController {
    private final SpecialityRestClient restClient;

    @GetMapping("list")
    public String getSpecialitiesList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("specialities", this.restClient.findAllSpecialities(filter));
        model.addAttribute("filter", filter);
        return "clinic/specialities/list";
    }

    @GetMapping("create")
    public String getNewSpecialityPage() {
        return "clinic/specialities/new_speciality";
    }

    @PostMapping("create")
    public String createSpeciality(NewSpecialityPayload payload, Model model, HttpServletResponse response) {
        try {
            Speciality speciality = this.restClient.createSpeciality(payload.name());
            return "redirect:/clinic/specialities/%d".formatted(speciality.id());
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/specialities/new_speciality";
        }
    }
}
