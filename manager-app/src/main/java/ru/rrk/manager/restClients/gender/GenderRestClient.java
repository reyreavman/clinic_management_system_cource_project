package ru.rrk.manager.restClients.gender;

import ru.rrk.manager.entity.Gender;

import java.util.List;
import java.util.Optional;

public interface GenderRestClient {
    List<Gender> findAllGenders();

    Gender createGender(String gender);

    Optional<Gender> findGender(int genderId);

    void updateGender(int genderId, String gender);

    void deleteGender(int genderId);
}
