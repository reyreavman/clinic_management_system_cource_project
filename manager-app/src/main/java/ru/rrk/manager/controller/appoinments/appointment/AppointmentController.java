package ru.rrk.manager.controller.appoinments.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rrk.manager.restClients.appointment.appointment.AppointmentRestClient;
import ru.rrk.manager.restClients.checkup.checkup.CheckupRestClient;
import ru.rrk.manager.restClients.pet.pet.PetRestClient;
import ru.rrk.manager.restClients.vet.vet.VetRestClient;

@Controller
@RequiredArgsConstructor
@RequestMapping("clinic/appointments/{appointmentId:\\d+}")
public class AppointmentController {
    private final AppointmentRestClient appointmentRestClient;
    private final PetRestClient petRestClient;
    private final VetRestClient vetRestClient;
    private final CheckupRestClient checkupRestClient;
}
