package ru.rrk.user.receptionist.mapper;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.viewModels.VetViewSummary;
import ru.rrk.user.receptionist.dto.vet.Vet;

public class VetViewSummaryConverter implements Converter<Vet, VetViewSummary> {
    @Override
    public VetViewSummary convert(Vet source) {
        return new VetViewSummary(source.getId(), source.getFirstName(), source.getLastName(), source.getFirstName().concat(" ").concat(source.getLastName()), source.getSpeciality().name());
    }
}
