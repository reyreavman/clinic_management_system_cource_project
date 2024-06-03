package ru.rrk.common.mapper.vet;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.common.viewModels.vet.VetView;
import ru.rrk.common.dto.vet.Vet;

public class VetViewConverter implements Converter<Vet, VetView> {
    @Override
    public VetView convert(Vet vet) {
        return new VetView(vet.getId(), vet.getFirstName(), vet.getLastName(), vet.getFirstName().concat(" ").concat(vet.getLastName()), vet.getSpeciality().name());
    }
}
