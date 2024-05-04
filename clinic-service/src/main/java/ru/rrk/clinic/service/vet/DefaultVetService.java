package ru.rrk.clinic.service.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.Speciality;
import ru.rrk.clinic.entity.Vet;
import ru.rrk.clinic.repository.vet.VetRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultVetService implements VetService {
    private final VetRepository repository;

    @Override
    public Iterable<Vet> findAllVets(String filter) {
        if (filter != null && !filter.isBlank())
            return this.repository.findAllByFirstNameLikeIgnoreCase("%" + filter + "%");
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Vet createVet(String firstName, String lastName, Set<Speciality> specialitySet) {
        return this.repository.save(new Vet(null, firstName, lastName, specialitySet));
    }

    @Override
    public Optional<Vet> findVet(int vetId) {
        return this.repository.findById(vetId);
    }

    @Override
    @Transactional
    public void updateVet(Integer id, String firstName, String lastName, Set<Speciality> specialitySet) {
        this.repository.findById(id)
                .ifPresentOrElse(vet -> {
                    vet.setFirstName(firstName);
                    vet.setLastName(lastName);
                    vet.setSpecialitySet(specialitySet);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteVet(Integer id) {
        this.repository.deleteById(id);
    }
}
