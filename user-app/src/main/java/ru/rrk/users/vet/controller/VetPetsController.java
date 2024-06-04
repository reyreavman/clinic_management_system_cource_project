package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.common.mapper.pet.PetViewPrimaryConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.VetRestClient;
import ru.rrk.common.restClient.pet.PetRestClient;
import ru.rrk.common.viewModels.vet.VetView;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/pets")
@RequiredArgsConstructor
public class VetPetsController {
    private final VetRestClient vetRestClient;
    private final PetRestClient petRestClient;

    private final VetViewConverter vetViewConverter;
    private final PetViewPrimaryConverter petViewPrimaryConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.vets-offices.vet.not_found"));
    }

    @GetMapping
    public String getListPage(Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null).stream()
                .map(this.petViewPrimaryConverter::convert).toList());
        return "clinic/vets/pets/list";
    }
}
