package ru.rrk.user.receptionist.mapper.checkup;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.checkup.Checkup;
import ru.rrk.user.receptionist.viewModels.checkup.CheckupViewPrimary;

public class CheckupViewPrimaryConverter implements Converter<Checkup, CheckupViewPrimary> {
    @Override
    public CheckupViewPrimary convert(Checkup checkup) {
        return CheckupViewPrimary.builder()
                .id(checkup.id())
                .date(checkup.date())
                .time(checkup.time())
                .petId(checkup.id())
                .petName(checkup.pet().name())
                .vetId(checkup.vet().getId())
                .vetFullName(checkup.vet().getFirstName().concat(" ").concat(checkup.vet().getLastName()))
                .type(checkup.checkupType().type())
                .state(checkup.checkupState().state())
                .result(checkup.checkupResult() != null ? checkup.checkupResult().description() : null)
                .build();
    }
}
