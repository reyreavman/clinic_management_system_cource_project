package ru.rrk.clinic.service.appointment.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.appointment.AppointmentResult;
import ru.rrk.clinic.repository.appointment.appointment.AppointmentRepository;
import ru.rrk.clinic.repository.appointment.result.AppointmentResultRepository;
import ru.rrk.clinic.repository.appointment.result.AppointmentResultStateRepository;
import ru.rrk.clinic.repository.diagnosis.DiagnosisRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAppointmentResultsService implements AppointmentResultsService {
    private final AppointmentResultRepository appointmentResultRepository;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentResultStateRepository appointmentResultStateRepository;
    private final DiagnosisRepository diagnosisRepository;

    @Override
    public Iterable<AppointmentResult> findAllResults() {
        return this.appointmentResultRepository.findAll();
    }

    @Override
    @Transactional
    public AppointmentResult createResult(Integer currentAppointmentId, Integer nextAppointmentId, Integer stateId, Integer diagnosisId, String advice, String prescription) {
        return this.appointmentResultRepository.save(AppointmentResult.builder()
                .id(null)
                .currentAppointment(this.appointmentRepository.findById(currentAppointmentId).orElseThrow(NoSuchFieldError::new))
                .nextAppointment(this.appointmentRepository.findById(nextAppointmentId).orElseThrow(NoSuchElementException::new))
                .state(this.appointmentResultStateRepository.findById(stateId).orElseThrow(NoSuchElementException::new))
                .diagnosis(this.diagnosisRepository.findById(diagnosisId).orElseThrow(NoSuchElementException::new))
                .advice(advice)
                .prescription(prescription)
                .build());
    }

    @Override
    public Optional<AppointmentResult> findResult(Integer appointmentResultId) {
        return this.appointmentResultRepository.findById(appointmentResultId);
    }

    @Override
    @Transactional
    public void updateResult(Integer appointmentResultId, Integer currentAppointmentId, Integer nextAppointmentId, Integer stateId, Integer diagnosisId, String advice, String prescription) {
        this.appointmentResultRepository.findById(appointmentResultId).ifPresentOrElse(
                appointmentResult -> {
                    appointmentResult.setCurrentAppointment(this.appointmentRepository.findById(currentAppointmentId).orElseThrow(NoSuchFieldError::new));
                    appointmentResult.setNextAppointment(this.appointmentRepository.findById(nextAppointmentId).orElseThrow(NoSuchElementException::new));
                    appointmentResult.setState(this.appointmentResultStateRepository.findById(stateId).orElseThrow(NoSuchElementException::new));
                    appointmentResult.setDiagnosis(this.diagnosisRepository.findById(diagnosisId).orElseThrow(NoSuchElementException::new));
                    appointmentResult.setAdvice(advice);
                    appointmentResult.setPrescription(prescription);
                }, () -> {
                    throw new NoSuchElementException();
                }
        );
    }

    @Override
    @Transactional
    public void deleteResult(Integer appointmentResultId) {
        this.appointmentResultRepository.deleteById(appointmentResultId);
    }
}
