package ru.rrk.user.receptionist.mapper.checkup;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.viewModels.checkup.CheckupViewSummary;
import ru.rrk.user.receptionist.dto.checkup.Checkup;

public class CheckupViewSummaryConverter implements Converter<Checkup, CheckupViewSummary> {
    @Override
    public CheckupViewSummary convert(Checkup checkup) {
        return CheckupViewSummary.builder()
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
