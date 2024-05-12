package ru.rrk.clinic.service.checkup.type;

import ru.rrk.clinic.entity.checkup.CheckupType;

import java.util.Optional;

public interface CheckupTypeService {
    Iterable<CheckupType> findAllTypes();

    CheckupType createType(String type);

    Optional<CheckupType> findType(int typeId);

    void updateType(Integer id, String type);

    void deleteType(Integer id);
}
