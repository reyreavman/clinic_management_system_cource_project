package ru.rrk.user.receptionist.mapper.vet;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.viewModels.vet.VetSummaryView;
import ru.rrk.user.receptionist.dto.vet.Vet;

public class VetSummaryViewConverter implements Converter<Vet, VetSummaryView> {
    @Override
    public VetSummaryView convert(Vet source) {
        return new VetSummaryView(source.getId(), source.getFirstName(), source.getLastName(), source.getFirstName().concat(" ").concat(source.getLastName()), source.getSpeciality().name());
    }
}
