package ru.rrk.manager.controller.receptionists;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.receptionists.payload.UpdateReceptionistPayload;
import ru.rrk.manager.entity.Receptionist;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.receptionist.ReceptionistRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/receptionists/{receptionistId:\\d+}")
@RequiredArgsConstructor
public class ReceptionistController {
    private final ReceptionistRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("receptionist")
    public Receptionist receptionist(@PathVariable("receptionistId") int receptionistId) {
        return this.restClient.findReceptionist(receptionistId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.receptionist.not_found"));
    }

    @GetMapping
    public String getReceptionist() {
        return "clinic/receptionists/receptionist";
    }

    @GetMapping("edit")
    public String getReceptionistEditPage() {
        return "clinic/receptionists/edit";
    }

    @PostMapping("edit")
    public String updateReceptionist(@ModelAttribute(name = "receptionist", binding = false) Receptionist receptionist,
                                     UpdateReceptionistPayload payload, Model model) {
        try {
            this.restClient.updateReceptionist(receptionist.getId(), payload.firstName(), payload.lastName());
            return "redirect:/clinic/receptionists/%d".formatted(receptionist.getId());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/receptionists/edit";
        }
    }

    @PostMapping("delete")
    public String deleteReceptionist(@ModelAttribute("receptionist") Receptionist receptionist) {
        this.restClient.deleteReceptionist(receptionist.getId());
        return "redirect:/clinic/receptionists/list";
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
