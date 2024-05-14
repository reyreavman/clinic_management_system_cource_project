package ru.rrk.clinic.service.vet.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.vet.Vet;
import ru.rrk.clinic.repository.vet.speciality.SpecialityRepository;
import ru.rrk.clinic.repository.vet.VetRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultVetService implements VetService {
    private final VetRepository vetRepository;
    private final SpecialityRepository specialityRepository;

    @Override
    public Iterable<Vet> findAllVets(String filter) {
        if (filter != null && !filter.isBlank())
            return this.vetRepository.findAllByFirstNameLikeIgnoreCase("%" + filter + "%");
        return this.vetRepository.findAll();
    }

    @Override
    @Transactional
    public Vet createVet(String firstName, String lastName, Integer specialityId) {
        return this.vetRepository.save(new Vet(null, firstName, lastName, this.specialityRepository.findById(specialityId).orElseThrow(NoSuchElementException::new)));
    }

    @Override
    public Optional<Vet> findVet(Integer vetId) {
        return this.vetRepository.findById(vetId);
    }

    @Override
    @Transactional
    public void updateVet(Integer id, String firstName, String lastName, Integer specialityId) {
        this.vetRepository.findById(id)
                .ifPresentOrElse(vet -> {
                    vet.setFirstName(firstName);
                    vet.setLastName(lastName);
                    vet.setSpeciality(this.specialityRepository.findById(specialityId).orElseThrow(NoSuchElementException::new));
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteVet(Integer id) {
        this.vetRepository.deleteById(id);
    }
}
