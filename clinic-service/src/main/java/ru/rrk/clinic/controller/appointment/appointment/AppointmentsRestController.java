package ru.rrk.clinic.controller.appointment.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.appointment.appointment.payload.NewAppointmentPayload;
import ru.rrk.clinic.entity.appointment.Appointment;
import ru.rrk.clinic.service.appointment.appointment.AppointmentService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments")
public class AppointmentsRestController {
    private final AppointmentService service;

    @GetMapping
    public Iterable<Appointment> findAppointments(Principal principal) {
        LoggerFactory.getLogger(AppointmentsRestController.class).info("Principal: {}", principal);
        return this.service.findAllAppointments();
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@Valid @RequestBody NewAppointmentPayload payload,
                                               BindingResult bindingResult,
                                               UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Appointment appointment = this.service.createAppointment(payload.petId(), payload.vetId(), payload.date(), payload.time(), payload.description(), payload.checkupId(), payload.receptionistId());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("clinic-api/appointments/{appointmentId}")
                            .build(Map.of("appointmentId", appointment.getId())))
                    .body(appointment);
        }
    }
}
