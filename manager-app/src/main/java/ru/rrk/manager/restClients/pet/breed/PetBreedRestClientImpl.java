package ru.rrk.manager.restClients.pet.breed;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.pets.breeds.payload.NewPetBreedPayload;
import ru.rrk.manager.controller.pets.breeds.payload.UpdatePetBreedPayload;
import ru.rrk.manager.entity.pet.PetBreed;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class PetBreedRestClientImpl implements PetBreedRestClient {
    private static final ParameterizedTypeReference<List<PetBreed>> BREEDS_TYPE_REFERENCE = new ParameterizedTypeReference<List<PetBreed>>() {
    };
    private final RestClient client;

    @Override
    public List<PetBreed> findAllBreeds(String filter) {
        return this.client
                .get()
                .uri("clinic-api/pets/breeds?filter={filter}", filter)
                .retrieve()
                .body(BREEDS_TYPE_REFERENCE);
    }

    @Override
    public PetBreed createBreed(String name, Integer typeId) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/pets/breeds")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewPetBreedPayload(name, typeId))
                    .retrieve()
                    .body(PetBreed.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<PetBreed> findBreed(int breedId) {
        try {
            return Optional.ofNullable(
                    this.client
                            .get()
                            .uri("clinic-api/pets/breeds/{breedId}", breedId)
                            .retrieve()
                            .body(PetBreed.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateBreed(int breedId, String name, Integer typeId) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/pets/breeds/{breedId}", breedId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdatePetBreedPayload(name, typeId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteBreed(int breedId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/pets/breeds/{breedId}", breedId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
