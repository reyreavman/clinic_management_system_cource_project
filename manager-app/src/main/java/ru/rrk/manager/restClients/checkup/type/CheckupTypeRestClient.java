package ru.rrk.manager.restClients.checkup.type;

import ru.rrk.manager.entity.checkup.CheckupType;

import java.util.List;
import java.util.Optional;

public interface CheckupTypeRestClient {
    List<CheckupType> findAllTypes();

    CheckupType createType(String type);

    Optional<CheckupType> findType(int typeId);

    void updateType(int typeId, String type);

    void deleteType(int typeId);
}
