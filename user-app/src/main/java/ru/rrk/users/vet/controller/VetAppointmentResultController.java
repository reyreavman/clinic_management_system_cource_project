package ru.rrk.users.vet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rrk.common.mapper.appointment.AppointmentSummaryViewConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;
import ru.rrk.common.restClient.*;
import ru.rrk.common.viewModels.appointment.AppointmentSummaryView;
import ru.rrk.common.viewModels.vet.VetView;
import ru.rrk.users.receptionist.controller.BadRequestException;

@Controller
@RequestMapping("clinic/vets/vet/{vetId:\\d+}/appointments/{appointmentId:\\d+}/result")
@RequiredArgsConstructor
public class VetAppointmentResultController {
    private final VetRestClient vetRestClient;
    private final AppointmentRestClient appointmentRestClient;
    private final AppointmentResultRestClient appointmentResultRestClient;
    private final AppointmentResultStateRestClient appointmentResultStateRestClient;
    private final DiagnosisRestClient diagnosisRestClient;
    private final DiseaseRestClient diseaseRestClient;

    private final VetViewConverter vetViewConverter;
    private final AppointmentSummaryViewConverter appointmentSummaryViewConverter;

    @ModelAttribute("vet")
    public VetView vet(@PathVariable("vetId") int vetId) {
        return this.vetRestClient.findVet(vetId).map(this.vetViewConverter::convert)
                .orElseThrow();
    }

    @ModelAttribute("appointment")
    public AppointmentSummaryView appointment(@PathVariable("appointmentId") int appointmentId) {
        return this.appointmentRestClient.findAppointment(appointmentId).map(this.appointmentSummaryViewConverter::convert)
                .orElseThrow();
    }

    @GetMapping("create")
    public String getNewResultPage(Model model) {
        model.addAttribute("diseases", this.diseaseRestClient.findAllDiseases(null));
        return "clinic/vets/appointments/results/create";
    }

    @PostMapping("create")
    public String createNewResult(NewDiagnosisPayload diagnosisPayload,
                                  NewAppointmentResultPayload resultPayload,
                                  Model model,
                                  @PathVariable("appointmentId") int appointmentId) {
        try {
            System.out.println(diagnosisPayload.toString());
            System.out.println(resultPayload.toString());
            /** В диагнозис пейлоад приходит дизиз айди, дескрипш, поля заполнены
             *  В резалт пейлоад не приходит каррентАппоинтментАЙДИ, некстАппоинтментАЙДИ, стейтАЙДИ, диагнозисАЙДИ
             */
            return null;
        } catch (BadRequestException exception) {
            model.addAttribute("errors", exception.getErrors());
            return "clinic/reception/receptionist/checkups/create";
        }
    }

}
