package ru.rrk.clinic.service.pet.label;

import ru.rrk.clinic.entity.pet.Label;

import java.util.Optional;

public interface LabelService {
    Iterable<Label> findAllLabels();

    Label createLabel(String value, String date);

    Optional<Label> findLabel(int labelId);

    void updateLabel(Integer id, String value, String date);

    void deleteLabel(Integer id);
}
