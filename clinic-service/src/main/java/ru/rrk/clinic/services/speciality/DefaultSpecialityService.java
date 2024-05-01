package ru.rrk.clinic.services.speciality;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rrk.clinic.entity.Speciality;
import ru.rrk.clinic.repository.speciality.SpecialityRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultSpecialityService implements SpecialityService {
    private final SpecialityRepository repository;
    @Override
    public Iterable<Speciality> findAllSpecialities(String filter) {
        if (filter != null && !filter.isBlank()) return this.repository.findAllByNameIgnoreCase("%" + filter + "%");
        return this.repository.findAll();
    }

    @Override
    public Speciality createSpeciality(String name) {
        return this.repository.save(new Speciality(null, name));
    }

    @Override
    public Optional<Speciality> findSpeciality(int specialityId) {
        return this.repository.findById(specialityId);
    }

    @Override
    public void updateSpeciality(Integer id, String name) {
        this.repository.findById(id)
                .ifPresentOrElse(speciality -> speciality.setName(name), () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteSpeciality(Integer id) {
        this.repository.deleteById(id);
    }
}
