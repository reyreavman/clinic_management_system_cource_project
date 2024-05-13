package ru.rrk.clinic.service.checkup.result;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.checkup.CheckupResult;
import ru.rrk.clinic.repository.checkup.result.CheckupResultRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckupResultServiceImpl implements CheckupResultService {
    private final CheckupResultRepository repository;

    @Override
    public Iterable<CheckupResult> findAllResults() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public CheckupResult createResult(String description) {
        return this.repository.save(new CheckupResult(null, description));
    }

    @Override
    public Optional<CheckupResult> findResult(int resultId) {
        return this.repository.findById(resultId);
    }

    @Override
    @Transactional
    public void updateResult(int resultId, String description) {
        this.repository.findById(resultId)
                .ifPresentOrElse(result -> result.setDescription(description),
                        () -> {
                            throw new NoSuchElementException();
                        });
    }

    @Override
    @Transactional
    public void deleteResult(int resultId) {
        this.repository.deleteById(resultId);
    }
}
