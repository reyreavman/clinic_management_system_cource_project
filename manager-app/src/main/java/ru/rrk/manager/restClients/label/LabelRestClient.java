package ru.rrk.manager.restClients.label;

import ru.rrk.manager.entity.Label;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LabelRestClient {
    List<Label> findAllLabels();

    Label createLabel(String value, Date date);

    Optional<Label> findLabel(int labelId);

    void updateLabel(int labelId, String value, Date date);

    void deleteLabel(int labelId);
}
