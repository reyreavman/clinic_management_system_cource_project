package ru.rrk.user.receptionist.converter;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.CheckupDTO;
import ru.rrk.user.receptionist.entity.checkup.Checkup;

public class CheckupDTOConverter implements Converter<Checkup, CheckupDTO> {
    @Override
    public CheckupDTO convert(Checkup checkup) {
        return CheckupDTO.builder()
                .id(checkup.id())
                .time(checkup.time())
                .petId(checkup.pet().id())
                .petName(checkup.pet().name())
                .vetId(checkup.vet().getId())
                .vetName(checkup.vet().getFirstName().concat(" ").concat(checkup.vet().getLastName()))
                .state(checkup.checkupState().state())
                .build();
    }
}
