package ru.rrk.clinic.service.disease;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.Disease;
import ru.rrk.clinic.repository.disease.DiseaseRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository repository;

    @Override
    public Iterable<Disease> findAllDiseases(String filter) {
        if (filter != null && !filter.isBlank())
            return this.repository.findAllByName("%" + filter + "%");
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Disease createDisease(int code, String description) {
        return this.repository.save(new Disease(null, code, description));
    }

    @Override
    public Optional<Disease> findDisease(int diseaseId) {
        return this.repository.findById(diseaseId);
    }

    @Override
    @Transactional
    public void updateDisease(Integer id, int code, String description) {
        this.repository.findById(id)
                .ifPresentOrElse(disease -> {
                    disease.setCode(code);
                    disease.setDescription(description);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteDisease(Integer id) {
        this.repository.deleteById(id);
    }
}
