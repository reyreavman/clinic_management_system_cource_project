package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.dto.pet.Pet;

import java.util.List;

@RequiredArgsConstructor
public class PetRestClient {
    private static final ParameterizedTypeReference<List<Pet>> PET_TYPE_REFERENCE = new ParameterizedTypeReference<List<Pet>>() {
    };
    private final RestClient client;

    public List<Pet> findAllPets(String filter) {
        return this.client
                .get()
                .uri("clinic-api/pets?filter={filter}", filter)
                .retrieve()
                .body(PET_TYPE_REFERENCE);
    }
}
