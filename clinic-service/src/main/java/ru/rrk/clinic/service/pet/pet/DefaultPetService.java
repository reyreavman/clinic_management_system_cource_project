package ru.rrk.clinic.service.pet.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.pet.Pet;
import ru.rrk.clinic.repository.client.ClientRepository;
import ru.rrk.clinic.repository.gender.GenderRepository;
import ru.rrk.clinic.repository.pet.breed.PetBreedRepository;
import ru.rrk.clinic.repository.pet.label.LabelRepository;
import ru.rrk.clinic.repository.pet.pet.PetRepository;
import ru.rrk.clinic.repository.pet.type.PetTypeRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultPetService implements PetService {
    private final PetRepository petRepository;
    private final ClientRepository clientRepository;
    private final PetTypeRepository typeRepository;
    private final PetBreedRepository breedRepository;
    private final GenderRepository genderRepository;
    private final LabelRepository labelRepository;

    @Override
    public Iterable<Pet> findAllPets(String filter) {
        if (filter != null && !filter.isBlank())
            return this.petRepository.findAllByNameLikeIgnoreCase(filter);
        return this.petRepository.findAll();
    }

    @Override
    @Transactional
    public Pet createPet(String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId) {
        return this.petRepository.save(
                Pet.builder()
                        .id(null)
                        .client(this.clientRepository.findById(clientId).orElseThrow(NoSuchElementException::new))
                        .type(this.typeRepository.findById(typeId).orElseThrow(NoSuchElementException::new))
                        .breed(this.breedRepository.findById(breedId).orElseThrow(NoSuchElementException::new))
                        .gender(this.genderRepository.findById(genderId).orElseThrow(NoSuchElementException::new))
                        .birthday(birthday)
                        .label(this.labelRepository.findById(labelId).orElseThrow(NoSuchElementException::new))
                        .build()
        );
    }

    @Override
    public Optional<Pet> findPet(int petId) {
        return this.petRepository.findById(petId);
    }

    @Override
    @Transactional
    public void updatePet(Integer petId, String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId) {
        this.petRepository.findById(petId)
                .ifPresentOrElse(pet -> {
                    pet.setName(name);
                    pet.setClient(this.clientRepository.findById(clientId).orElseThrow(NoSuchElementException::new));
                    pet.setType(this.typeRepository.findById(typeId).orElseThrow(NoSuchElementException::new));
                    pet.setBreed(this.breedRepository.findById(breedId).orElseThrow(NoSuchElementException::new));
                    pet.setGender(this.genderRepository.findById(genderId).orElseThrow(NoSuchElementException::new));
                    pet.setBirthday(birthday);
                    pet.setLabel(this.labelRepository.findById(labelId).orElseThrow(NoSuchElementException::new));
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deletePet(Integer petId) {
        this.petRepository.deleteById(petId);
    }
}
