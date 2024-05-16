package ru.rrk.clinic.service.appointment.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.appointment.Appointment;
import ru.rrk.clinic.repository.appointment.appointment.AppointmentRepository;
import ru.rrk.clinic.repository.checkup.CheckupRepository;
import ru.rrk.clinic.repository.pet.pet.PetRepository;
import ru.rrk.clinic.repository.vet.VetRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final CheckupRepository checkupRepository;

    @Override
    public Iterable<Appointment> findAllAppointments() {
        return this.appointmentRepository.findAll();
    }

    @Override
    @Transactional
    public Appointment createAppointment(Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId) {
        if (checkupId != null) {
            return this.appointmentRepository.save(
                    Appointment.builder()
                            .pet(this.petRepository.findById(petId).orElseThrow(NoSuchElementException::new))
                            .vet(this.vetRepository.findById(vetId).orElseThrow(NoSuchElementException::new))
                            .date(date)
                            .time(time)
                            .description(description)
                            .checkup(this.checkupRepository.findById(checkupId).orElseThrow(NoSuchElementException::new))
                            .build());
        }
        return this.appointmentRepository.save(
                Appointment.builder()
                .pet(this.petRepository.findById(petId).orElseThrow(NoSuchElementException::new))
                .vet(this.vetRepository.findById(vetId).orElseThrow(NoSuchElementException::new))
                .date(date)
                .time(time)
                .description(description)
                .build());
    }

    @Override
    public Optional<Appointment> findAppointment(Integer appointmentId) {
        return this.appointmentRepository.findById(appointmentId);
    }

    @Override
    @Transactional
    public void updateAppointment(Integer appointmentId, Integer petId, Integer vetId, LocalDate date, LocalTime time, String description, Integer checkupId) {
        this.appointmentRepository.findById(appointmentId)
                .ifPresentOrElse(appointment -> {
                            appointment.setPet(this.petRepository.findById(petId).orElseThrow(NoSuchElementException::new));
                            appointment.setVet(this.vetRepository.findById(vetId).orElseThrow(NoSuchElementException::new));
                            appointment.setDate(date);
                            appointment.setTime(time);
                            appointment.setDescription(description);
                            if (checkupId != null) appointment.setCheckup(this.checkupRepository.findById(checkupId).orElseThrow(NoSuchElementException::new));
                        }, () -> {
                            throw new NoSuchElementException();
                        }
                );
    }

    @Override
    @Transactional
    public void deleteAppointment(Integer appointmentId) {
        this.appointmentRepository.deleteById(appointmentId);
    }
}
