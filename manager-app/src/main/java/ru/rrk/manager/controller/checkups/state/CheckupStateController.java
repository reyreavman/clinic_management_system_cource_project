package ru.rrk.manager.controller.checkups.state;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.checkups.state.payload.UpdateCheckupStatePayload;
import ru.rrk.manager.entity.checkup.CheckupState;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.state.CheckupStateRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/checkups/states/{stateId:\\d+}")
@RequiredArgsConstructor
public class CheckupStateController {
    private final CheckupStateRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("state")
    public CheckupState state(@PathVariable("stateId") int stateId) {
        return this.restClient.findState(stateId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.state.not_found"));
    }

    @GetMapping
    public String getState() {
        return "clinic/checkups/states/state";
    }

    @GetMapping("edit")
    public String getStateEditPage() {
        return "clinic/checkups/states/edit";
    }

    @PostMapping("edit")
    public String updateState(@ModelAttribute(name = "state", binding = false) CheckupState state,
                              UpdateCheckupStatePayload payload, Model model) {
        try {
            this.restClient.updateState(state.id(), payload.state());
            return "redirect:/clinic/checkups/states/%d" .formatted(state.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/states/edit";
        }
    }

    @PostMapping("delete")
    public String deleteState(@ModelAttribute("state") CheckupState state) {
        this.restClient.deleteState(state.id());
        return "redirect:/clinic/checkups/states/list";
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
