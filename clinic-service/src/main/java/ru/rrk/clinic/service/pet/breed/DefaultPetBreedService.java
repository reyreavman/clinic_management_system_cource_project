package ru.rrk.clinic.service.pet.breed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.pet.PetBreed;
import ru.rrk.clinic.repository.pet.breed.PetBreedRepository;
import ru.rrk.clinic.repository.pet.type.PetTypeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultPetBreedService implements PetBreedService {
    private final PetBreedRepository breedRepository;
    private final PetTypeRepository typeRepository;

    @Override
    public Iterable<PetBreed> findAllBreeds(String filter) {
        if (filter != null && !filter.isBlank())
            return this.breedRepository.findAllByNameLikeIgnoreCase("%" + filter + "%");
        return this.breedRepository.findAll();
    }

    @Override
    @Transactional
    public PetBreed createBreed(String name, Integer petTypeId) {
        return this.breedRepository.save(new PetBreed(null, name, this.typeRepository.findById(petTypeId).orElseThrow(NoSuchElementException::new)));
    }

    @Override
    public Optional<PetBreed> findBreed(int breedId) {
        return this.breedRepository.findById(breedId);
    }

    @Override
    @Transactional
    public void updateBreed(Integer id, String name, Integer petTypeId) {
        this.breedRepository.findById(id)
                .ifPresentOrElse(breed -> {
                    breed.setName(name);
                    breed.setType(this.typeRepository.findById(petTypeId).orElseThrow(NoSuchElementException::new));
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteBreed(Integer id) {
        this.breedRepository.deleteById(id);
    }
}
