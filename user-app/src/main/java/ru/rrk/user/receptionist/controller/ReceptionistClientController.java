package ru.rrk.user.receptionist.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.user.receptionist.dto.Client;
import ru.rrk.user.receptionist.dto.Receptionist;
import ru.rrk.user.receptionist.mapper.PetViewPrimaryConverter;
import ru.rrk.user.receptionist.restClient.ClientRestClient;
import ru.rrk.user.receptionist.restClient.PetRestClient;
import ru.rrk.user.receptionist.restClient.ReceptionistRestClient;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/reception/receptionist/{receptionistId:\\d+}/clients/{clientId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistClientController {
    private final ReceptionistRestClient receptionistRestClient;
    private final ClientRestClient clientRestClient;
    private final PetRestClient petRestClient;
    private final PetViewPrimaryConverter petViewPrimaryConverter;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.receptionistRestClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.receptionist.not_found"));
    }

    @ModelAttribute("client")
    public Client client(@PathVariable("clientId") int clientId) {
         return this.clientRestClient.findClient(clientId)
                 .orElseThrow(() -> new NoSuchElementException("clinic.errors.reception.client.not_found"));
    }

    @GetMapping
    public String getClientInfoPage(@PathVariable("clientId") int clientId, Model model) {
        model.addAttribute("pets", this.petRestClient.findAllPets(null).stream().filter(pet -> pet.client().id() == clientId).map(this.petViewPrimaryConverter::convert).toList());
        return "clinic/reception/receptionist/clients/client";
    }
}
