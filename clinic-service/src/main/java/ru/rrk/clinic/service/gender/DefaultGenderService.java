package ru.rrk.clinic.service.gender;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.Gender;
import ru.rrk.clinic.repository.gender.GenderRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultGenderService implements GenderService {
    private final GenderRepository repository;

    @Override
    public Iterable<Gender> findAllGenders() {
        return this.repository.findAll();
    }

    @Override
    @Transactional
    public Gender createGender(String gender) {
        return this.repository.save(new Gender(null, gender));
    }

    @Override
    public Optional<Gender> findGender(int genderId) {
        return this.repository.findById(genderId);
    }

    @Override
    @Transactional
    public void updateGender(int genderId, String genderValue) {
        this.repository.findById(genderId)
                .ifPresentOrElse(gender -> gender.setGender(genderValue),
                        () -> {throw new NoSuchElementException();});
    }

    @Override
    @Transactional
    public void deleteGender(int genderId) {
        this.repository.deleteById(genderId);
    }
}
