package ru.rrk.clinic.service.label;

import ru.rrk.clinic.entity.Label;

import java.util.Date;
import java.util.Optional;

public interface LabelService {
    Iterable<Label> findAllLabels();

    Label createLabel(String value, Date date);

    Optional<Label> findLabel(int labelId);

    void updateLabel(Integer id, String value, Date date);

    void deleteLabel(Integer id);
}
