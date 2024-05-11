package ru.rrk.clinic.service.receptionist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rrk.clinic.entity.Receptionist;
import ru.rrk.clinic.repository.receptionist.ReceptionistRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceptionistServiceImpl implements ReceptionistService {
    private final ReceptionistRepository repository;

    @Override
    public Iterable<Receptionist> findAllReceptionists(String filter) {
        return this.repository.findAll();
    }

    @Override
    public Receptionist createReceptionist(String firstName, String lastName) {
        return this.repository.save(new Receptionist(null, firstName, lastName));
    }

    @Override
    public Optional<Receptionist> findReceptionist(int receptionistId) {
        return this.repository.findById(receptionistId);
    }

    @Override
    public void updateReceptionist(Integer id, String firstName, String lastName) {
        this.repository.findById(id).
                ifPresentOrElse(receptionist -> {
                    receptionist.setFirstName(firstName);
                    receptionist.setLastName(lastName);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    public void deleteReceptionist(Integer id) {
        this.repository.deleteById(id);
    }
}
