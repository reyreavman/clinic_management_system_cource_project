package ru.rrk.clinic.service.checkup.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.checkup.CheckupState;
import ru.rrk.clinic.repository.checkup.state.CheckupStateRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultCheckupStateService implements CheckupStateService {
    private final CheckupStateRepository repository;

    @Override
    public Iterable<CheckupState> findAllStates() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public CheckupState createState(String state) {
        return this.repository.save(new CheckupState(null, state));
    }

    @Override
    public Optional<CheckupState> findState(int stateId) {
        return this.repository.findById(stateId);
    }

    @Override
    @Transactional
    public void updateState(Integer id, String type) {
        this.repository.findById(id)
                .ifPresentOrElse(state -> state.setState(type), () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteState(Integer id) {
        this.repository.deleteById(id);
    }
}
