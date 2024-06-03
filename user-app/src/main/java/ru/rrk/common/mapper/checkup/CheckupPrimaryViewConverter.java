package ru.rrk.common.mapper.checkup;

import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import ru.rrk.common.dto.checkup.Checkup;
import ru.rrk.common.viewModels.checkup.CheckupPrimaryView;

public class CheckupPrimaryViewConverter implements Converter<Checkup, CheckupPrimaryView> {
    @Override
    @Nullable
    public CheckupPrimaryView convert(Checkup checkup) {
        if (checkup != null) {
            return CheckupPrimaryView.builder()
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
        return null;
    }
}
