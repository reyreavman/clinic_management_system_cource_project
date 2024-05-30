package ru.rrk.user.receptionist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.PetViewPrimaryConverter;
import ru.rrk.user.receptionist.restClient.PetRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/clinic/reception/receptionist/{receptionistId:\\d+}/pets")
@RequiredArgsConstructor
public class ReceptionistPetsController {
    private final ReceptionistRestClient receptionistRestClient;
    private final PetRestClient petRestClient;

    private final PetViewPrimaryConverter petViewPrimaryConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.reception.receptionist.not_found"));
    }

    @GetMapping("list")
    public String getPetsListPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null).stream().map(this.petViewPrimaryConverter::convert).toList());
        return "clinic/reception/receptionist/pets/list";
    }
}
