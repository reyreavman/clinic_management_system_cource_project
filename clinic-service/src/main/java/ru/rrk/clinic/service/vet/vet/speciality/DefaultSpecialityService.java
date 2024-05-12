package ru.rrk.clinic.service.vet.vet.speciality;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.vet.Speciality;
import ru.rrk.clinic.repository.vet.speciality.SpecialityRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultSpecialityService implements SpecialityService {
    private final SpecialityRepository repository;

    @Override
    public Iterable<Speciality> findAllSpecialities(String filter) {
        if (filter != null && !filter.isBlank())
            return this.repository.findAllByName("%" + filter + "%");
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Speciality createSpeciality(String name) {
        return this.repository.save(new Speciality(null, name));
    }

    @Override
    public Optional<Speciality> findSpeciality(int specialityId) {
        return this.repository.findById(specialityId);
    }

    @Override
    @Transactional
    public void updateSpeciality(Integer id, String name) {
        this.repository.findById(id)
                .ifPresentOrElse(speciality -> speciality.setName(name), () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteSpeciality(Integer id) {
        this.repository.deleteById(id);
    }
}
