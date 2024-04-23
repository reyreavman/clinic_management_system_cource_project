package ru.rrk.manager.controller.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rrk.manager.client.BadRequestException;
import ru.rrk.manager.client.client.ClientRestClient;
import ru.rrk.manager.controller.clients.payload.NewClientPayload;
import ru.rrk.manager.entity.Client;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/clients")
public class ClientsController {
    private final ClientRestClient restClient;

    @GetMapping("list")
    public String getClientsList(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("clients", this.restClient.findAllClients(filter));
        model.addAttribute("filter", filter);
        return "clinic/clients/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "clinic/clients/new_client";
    }

    @PostMapping("create")
    public String createClient(NewClientPayload payload, Model model) {
        try {
            Client client = this.restClient.createClient(payload.firstName(), payload.lastName(), payload.phoneNumber(), payload.email());
            return "redirect:/clinic/clients/%d".formatted(client.getId());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/clients/new_client";
        }
    }
}
