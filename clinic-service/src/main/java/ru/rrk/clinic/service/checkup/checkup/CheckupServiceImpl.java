package ru.rrk.clinic.service.checkup.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rrk.clinic.entity.checkup.Checkup;
import ru.rrk.clinic.repository.checkup.CheckupRepository;
import ru.rrk.clinic.repository.checkup.result.CheckupResultRepository;
import ru.rrk.clinic.repository.checkup.state.CheckupStateRepository;
import ru.rrk.clinic.repository.checkup.type.CheckupTypeRepository;
import ru.rrk.clinic.repository.pet.pet.PetRepository;
import ru.rrk.clinic.repository.vet.VetRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckupServiceImpl implements CheckupService {
    private final CheckupRepository checkupRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final CheckupTypeRepository checkupTypeRepository;
    private final CheckupStateRepository checkupStateRepository;
    private final CheckupResultRepository checkupResultRepository;

    @Override
    public Iterable<Checkup> findAllCheckups() {
        return this.checkupRepository.findAll();
    }

    @Override
    @Transactional
    public Checkup createCheckup(LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId) {
        return this.checkupRepository.save(Checkup.builder()
                .id(null)
                .time(time)
                .date(date)
                .pet(this.petRepository.findById(petId).orElseThrow(NoSuchElementException::new))
                .vet(this.vetRepository.findById(vetId).orElseThrow(NoSuchElementException::new))
                .checkupType(this.checkupTypeRepository.findById(checkupTypeId).orElseThrow(NoSuchElementException::new))
                .checkupState(this.checkupStateRepository.findById(checkupStateId).orElseThrow(NoSuchElementException::new))
                .checkupResult(this.checkupResultRepository.findById(checkupResultId).orElseThrow(NoSuchElementException::new))
                .build());
    }

    @Override
    public Optional<Checkup> findCheckup(Integer checkupId) {
        return this.checkupRepository.findById(checkupId);
    }

    @Override
    @Transactional
    public void updateCheckup(Integer checkupId, LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId) {
        this.checkupRepository.findById(checkupId)
                .ifPresentOrElse(checkup -> {
                    checkup.setDate(date);
                    checkup.setTime(time);
                    checkup.setPet(this.petRepository.findById(petId).orElseThrow(NoSuchElementException::new));
                    checkup.setVet(this.vetRepository.findById(vetId).orElseThrow(NoSuchElementException::new));
                    checkup.setCheckupType(this.checkupTypeRepository.findById(checkupTypeId).orElseThrow(NoSuchElementException::new));
                    checkup.setCheckupState(this.checkupStateRepository.findById(checkupStateId).orElseThrow(NoSuchElementException::new));
                    checkup.setCheckupResult(this.checkupResultRepository.findById(checkupResultId).orElseThrow(NoSuchElementException::new));
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteCheckup(Integer checkupId) {
        this.checkupRepository.deleteById(checkupId);
    }
}
