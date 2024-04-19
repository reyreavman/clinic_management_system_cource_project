package ru.rrk.manager.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.manager.entity.ClinicUser;

import java.util.Optional;

public interface ClinicUserRepository extends CrudRepository<ClinicUser, Integer> {
    Optional<ClinicUser> findByUsername(String username);
}
