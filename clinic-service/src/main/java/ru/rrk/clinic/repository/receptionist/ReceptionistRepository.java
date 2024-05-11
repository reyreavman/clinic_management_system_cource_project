package ru.rrk.clinic.repository.receptionist;

import org.springframework.data.repository.CrudRepository;
import ru.rrk.clinic.entity.Receptionist;

public interface ReceptionistRepository extends CrudRepository<Receptionist, Integer> {
}
