package ru.rrk.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.client.BadRequestException;
import ru.rrk.manager.client.client.ClientRestClient;
import ru.rrk.manager.controller.payload.UpdateClientPayload;
import ru.rrk.manager.entity.Client;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/clients/{clientId:\\d+}")
@RequiredArgsConstructor
public class ClientController {
    private final ClientRestClient clientRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("client")
    public Client client(@PathVariable("clientId") int clientId) {
        return this.clientRestClient.findClient(clientId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.client.not_found"));
    }

    @GetMapping
    public String getClient() {
        return "clinic/clients/client";
    }

    @GetMapping("edit")
    public String getClientEditPage() {
        return "clinic/clients/edit";
    }

    @PostMapping("edit")
    public String updateClient(@ModelAttribute(name = "client", binding = false) Client client,
                               UpdateClientPayload payload,
                               Model model) {
        try {
            this.clientRestClient.updateClient(client.getId(), payload.firstName(), payload.lastName(), payload.phoneNumber(), payload.email());
            return "redirect:/clinic/clients/%d".formatted(client.getId());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/clients/edit";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("client") Client client) {
        this.clientRestClient.deleteClient(client.getId());
        return "redirect:/clinic/clients/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
