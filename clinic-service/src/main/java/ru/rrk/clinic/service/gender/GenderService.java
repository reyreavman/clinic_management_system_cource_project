package ru.rrk.clinic.service.gender;


import ru.rrk.clinic.entity.Gender;

import java.util.Optional;

public interface GenderService {
    Iterable<Gender> findAllGenders();

    Gender createGender(String gender);

    Optional<Gender> findGender(int genderId);

    void updateGender(int genderId, String gender);

    void deleteGender(int genderId);
}
