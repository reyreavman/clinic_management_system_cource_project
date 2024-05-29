package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.dto.pet.Breed;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetBreedRestClient {
    private static final ParameterizedTypeReference<List<Breed>> BREED_TYPE_REFERENCE = new ParameterizedTypeReference<List<Breed>>() {
    };
    private final RestClient restClient;

    public List<Breed> findAllBreeds(String filter) {
        return this.restClient
                .get()
                .uri("clinic-api/pets/breeds?filter={filter}", filter)
                .retrieve()
                .body(BREED_TYPE_REFERENCE);
    }

    public Optional<Breed> findBreed(int breedId) {
        try {
            return Optional.ofNullable(
                    this.restClient
                            .get()
                            .uri("clinic-api/pets/breeds/{breedId}", breedId)
                            .retrieve()
                            .body(Breed.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
