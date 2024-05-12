package ru.rrk.clinic.service.pet.label;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.pet.Label;
import ru.rrk.clinic.repository.pet.label.LabelRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultLabelService implements LabelService {
    private final LabelRepository repository;

    @Override
    public Iterable<Label> findAllLabels() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Label createLabel(String value, String date) {
        return this.repository.save(new Label(null, value, LocalDate.parse(date)));
    }

    @Override
    public Optional<Label> findLabel(int labelId) {
        return this.repository.findById(labelId);
    }

    @Override
    @Transactional
    public void updateLabel(Integer id, String value, String date) {
        this.repository.findById(id)
                .ifPresentOrElse(label -> {
                    label.setValue(value);
                    label.setDate(LocalDate.parse(date));
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteLabel(Integer id) {
        this.repository.deleteById(id);
    }
}
