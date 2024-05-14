package ru.rrk.manager.controller.checkups.result;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.manager.controller.checkups.result.payload.UpdateCheckupResultPayload;
import ru.rrk.manager.entity.checkup.CheckupResult;
import ru.rrk.manager.restClients.BadRequestException;
import ru.rrk.manager.restClients.checkup.result.CheckupResultRestClient;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/checkups/results/{resultId:\\d+}")
@RequiredArgsConstructor
public class CheckupResultController {
    private final CheckupResultRestClient restClient;
    private final MessageSource messageSource;

    @ModelAttribute("result")
    public CheckupResult result(@PathVariable("resultId") int resultId) {
        return this.restClient.findResult(resultId)
                .orElseThrow(() -> new NoSuchElementException("clinic.errors.checkup.result.not_found"));
    }

    @GetMapping
    public String getResult() {
        return "clinic/checkups/results/result";
    }

    @GetMapping("edit")
    public String getResultEditPage() {
        return "clinic/checkups/results/edit";
    }

    @PostMapping("edit")
    public String updateResult(@ModelAttribute(name = "result", binding = false) CheckupResult result,
                               UpdateCheckupResultPayload payload, Model model) {
        try {
            this.restClient.updateResult(result.id(), payload.description());
            return "redirect:/clinic/checkups/results/%d" .formatted(result.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "clinic/checkups/results/edit";
        }
    }

    @PostMapping("delete")
    public String deleteResult(@ModelAttribute("result") CheckupResult result) {
        this.restClient.deleteResult(result.id());
        return "redirect:/clinic/checkups/results/list";
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
