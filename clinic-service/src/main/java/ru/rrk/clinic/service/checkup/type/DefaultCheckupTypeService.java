package ru.rrk.clinic.service.checkup.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.checkup.CheckupType;
import ru.rrk.clinic.repository.checkup.type.CheckupTypeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultCheckupTypeService implements CheckupTypeService {
    private final CheckupTypeRepository repository;

    @Override
    public Iterable<CheckupType> findAllTypes() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public CheckupType createType(String type) {
        return this.repository.save(new CheckupType(null, type));
    }

    @Override
    public Optional<CheckupType> findType(int typeId) {
        return this.repository.findById(typeId);
    }

    @Override
    @Transactional
    public void updateType(Integer id, String type) {
        this.repository.findById(id)
                .ifPresentOrElse(checkupType -> checkupType.setType(type),
                        () -> {
                            throw new NoSuchElementException();
                        });
    }

    @Override
    @Transactional
    public void deleteType(Integer id) {
        this.repository.deleteById(id);
    }
}
