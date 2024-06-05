package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.common.dto.Diagnosis;
import ru.rrk.common.dto.appointment.AppointmentResult;
import ru.rrk.common.dto.appointment.AppointmentResultState;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.*;
import ru.rrk.common.viewModels.vet.VetView;
import ru.rrk.users.receptionist.controller.BadRequestException;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/appointments/{appointmentId:\\d+}/result")
@RequiredArgsConstructor
public class VetAppointmentResultController {
    private final VetRestClient vetRestClient;
    private final AppointmentResultRestClient appointmentResultRestClient;
    private final AppointmentResultStateRestClient appointmentResultStateRestClient;
    private final DiagnosisRestClient diagnosisRestClient;
    private final DiseaseRestClient diseaseRestClient;

    private final VetViewConverter vetViewConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow();
    }

    @ModelAttribute("appointmentId")
    public int appointmentId(@PathVariable("appointmentId") int appointmentId) {
        return appointmentId;
    }

    @GetMapping("create")
    public String getNewResultPage(Model model) {
        model.addAttribute("diseases", this.diseaseRestClient.findAllDiseases(null));
        return "clinic/vets/appointments/results/create";
    }

    @PostMapping("create")
    public String createNewResult(NewDiagnosisPayload diagnosisPayload,
                                  NewAppointmentResultPayload resultPayload, Model model,
                                  @PathVariable("appointmentId") int appointmentId,
                                  @PathVariable("vetId") int vetId
    ) {
        try {
            Integer stateId = this.appointmentResultStateRestClient.findAllStates().stream()
                    .filter(state -> state.state().equalsIgnoreCase("завершён")).findFirst()
                    .map(AppointmentResultState::id)
                    .orElseThrow(NoSuchElementException::new);
            Diagnosis diagnosis = this.diagnosisRestClient.createDiagnosis(
                    diagnosisPayload.diseaseId(),
                    diagnosisPayload.description());
            AppointmentResult appointmentResult = this.appointmentResultRestClient.createAppointmentResult(
                    appointmentId,
                    null,
                    stateId,
                    diagnosis.id(),
                    resultPayload.advice(),
                    resultPayload.prescription()
            );
            return "redirect:/clinic/vets/vet/%d/appointments/%d".formatted(vetId, appointmentId);
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "clinic/vets/appointments/results/create";
        }
    }

}
