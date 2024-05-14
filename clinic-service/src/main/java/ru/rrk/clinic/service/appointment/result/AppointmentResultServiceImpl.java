package ru.rrk.clinic.service.appointment.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rrk.clinic.entity.appointment.AppointmentsResultState;
import ru.rrk.clinic.repository.appointment.result.AppointmentResultStateRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentResultServiceImpl implements AppointmentResultService {
    private final AppointmentResultStateRepository repository;

    @Override
    public Iterable<AppointmentsResultState> findAllStates() {
        return this.repository.findAll();
    }

    @Override
    public AppointmentsResultState createState(int stateId, String state) {
        return this.repository.save(new AppointmentsResultState(null, state));
    }

    @Override
    public Optional<AppointmentsResultState> findState(int stateId) {
        return this.repository.findById(stateId);
    }

    @Override
    public void updateState(int stateId, String state) {
        this.repository.findById(stateId)
                .ifPresentOrElse(curState -> curState.setState(state),
                        () -> {
                            throw new NoSuchElementException();
                        });
    }

    @Override
    public void deleteState(int stateId) {
        this.repository.deleteById(stateId);
    }
}
