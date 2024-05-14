package ru.rrk.clinic.service.checkup.checkup;

import ru.rrk.clinic.entity.checkup.Checkup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface CheckupService {
    Iterable<Checkup> findAllCheckups();

    Checkup createCheckup(LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId);

    Optional<Checkup> findCheckup(Integer checkupId);

    void updateCheckup(Integer checkupId, LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId);

    void deleteCheckup(Integer checkupId);
}
