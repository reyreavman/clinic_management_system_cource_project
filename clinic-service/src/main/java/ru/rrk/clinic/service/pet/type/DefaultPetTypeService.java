package ru.rrk.clinic.service.pet.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.pet.PetType;
import ru.rrk.clinic.repository.pet.type.PetTypeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultPetTypeService implements PetTypeService {
    private final PetTypeRepository repository;

    @Override
    public Iterable<PetType> findAllTypes(String filter) {
        if (filter != null && !filter.isBlank())
            return this.repository.findAllByName(filter);
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public PetType createType(String name) {
        return this.repository.save(new PetType(null, name));
    }

    @Override
    public Optional<PetType> findType(int typeId) {
        return this.repository.findById(typeId);
    }

    @Override
    @Transactional
    public void updateType(Integer typeId, String name) {
        this.repository.findById(typeId)
                .ifPresentOrElse(type -> type.setName(name),
                        () -> {
                            throw new NoSuchElementException();
                        });
    }

    @Override
    @Transactional
    public void deleteType(Integer typeId) {
        this.repository.deleteById(typeId);
    }
}
