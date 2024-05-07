package ru.rrk.clinic.service.label;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.Label;
import ru.rrk.clinic.repository.label.LabelRepository;

import java.util.Date;
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
    public Label createLabel(String value, Date date) {
        return this.repository.save(new Label(null, value, date));
    }

    @Override
    public Optional<Label> findLabel(int labelId) {
        return this.repository.findById(labelId);
    }

    @Override
    @Transactional
    public void updateLabel(Integer id, String value, Date date) {
        this.repository.findById(id)
                .ifPresentOrElse(label -> {
                    label.setValue(value);
                    label.setDate(date);
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
