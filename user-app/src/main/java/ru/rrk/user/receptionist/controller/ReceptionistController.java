package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.entity.Receptionist;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/{receptionistId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistController {
    private final ReceptionistRestClient restClient;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.restClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.receptionist.not_found"));
    }

    @GetMapping
    public String getReceptionistWorkPage() {
        return "clinic/receptionist/work_page";
    }
}
