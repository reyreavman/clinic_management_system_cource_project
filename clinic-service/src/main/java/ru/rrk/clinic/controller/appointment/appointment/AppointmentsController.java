package ru.rrk.clinic.controller.appointment.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.rrk.clinic.controller.appointment.appointment.payload.NewAppointmentPayload;
import ru.rrk.clinic.entity.appointment.Appointment;
import ru.rrk.clinic.service.appointment.appointment.AppointmentService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("clinic-api/appointments")
public class AppointmentsController {
    private final AppointmentService service;

    @GetMapping
    public Iterable<Appointment> findAppointments(Principal principal) {
        LoggerFactory.getLogger(AppointmentsController.class).info("Principal: {}", principal);
        return this.service.findAllAppointments();
    }

    public ResponseEntity<?> createAppointment(@Valid @RequestBody NewAppointmentPayload payload,
                                               BindingResult bindingResult,
                                               UriComponentsBuilder uriComponentsBuilder) {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) throw exception;
            else throw new BindException(bindingResult);
        } else {
            Appointment appointment = this.service.createAppointment(payload.petId(), payload.vetId(), payload.date(), payload.time(), payload.description(), payload.checkupId());
        }
        }
    }
