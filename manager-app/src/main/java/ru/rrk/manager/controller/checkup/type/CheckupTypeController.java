package ru.rrk.manager.controller.checkup.type;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.checkup.type.payload.UpdateCheckupTypePayload;
import ru.rrk.manager.entity.checkup.CheckupType;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.type.CheckupTypeRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/checkups/types/{typeId:\\d+}")
@RequiredArgsConstructor
public class CheckupTypeController {
    private final CheckupTypeRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("type")
    public CheckupType type(@PathVariable("typeId") int typeId) {
        return this.restClient.findType(typeId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.type.not_found"));
    }

    @GetMapping
    public String getType() {
        return "clinic/checkups/types/type";
    }

    @GetMapping("edit")
    public String getTypeEditPage() {
        return "clinic/checkups/types/edit";
    }

    @PostMapping("edit")
    public String updateType(@ModelAttribute(name = "type", binding = false) CheckupType type,
                             UpdateCheckupTypePayload payload, Model model) {
        try {
            this.restClient.updateType(type.id(), payload.type());
            return "redirect:/clinic/checkups/types/%d".formatted(type.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/types/edit";
        }
    }

    @PostMapping("delete")
    public String deleteType(@ModelAttribute("type") CheckupType type) {
        this.restClient.deleteType(type.id());
        return "redirect:/clinic/checkups/types/list";
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
