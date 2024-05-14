package ru.rrk.manager.restClients.checkup.checkup;

import ru.rrk.manager.entity.checkup.Checkup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CheckupRestClient {
    List<Checkup> findCheckups();

    Checkup createCheckup(LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId);

    Optional<Checkup> findCheckup(int checkupId);

    void updateCheckup(int checkupId, LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId);

    void deleteCheckup(int checkupId);
}
