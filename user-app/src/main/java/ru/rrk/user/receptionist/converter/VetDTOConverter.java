package ru.rrk.user.receptionist.converter;

import org.springframework.core.convert.converter.Converter;
import ru.rrk.user.receptionist.dto.VetDTO;
import ru.rrk.user.receptionist.entity.vet.Vet;

public class VetDTOConverter implements Converter<Vet, VetDTO> {
    @Override
    public VetDTO convert(Vet source) {
        return new VetDTO(source.getId(), source.getFirstName(), source.getLastName(), source.getFirstName().concat(" ").concat(source.getLastName()), source.getSpeciality().name());
    }
}
