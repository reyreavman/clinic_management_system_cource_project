package ru.rrk.common.restClient.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.receptionist.controller.pet.payload.NewPetPayload;
import ru.rrk.users.receptionist.controller.pet.payload.UpdatePetPayload;
import ru.rrk.common.dto.pet.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class PetRestClient {
    private static final ParameterizedTypeReference<List<Pet>> PET_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient client;

    public List<Pet> findAllPets(String filter) {
        return this.client
                .get()
                .uri("clinic-api/pets?filter={filter}", filter)
                .retrieve()
                .body(PET_TYPE_REFERENCE);
    }

    public Pet createPet(String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/pets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewPetPayload(name, clientId, typeId, breedId, genderId, labelId, birthday))
                    .retrieve()
                    .body(Pet.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public Optional<Pet> findPet(int petId) {
        try {
            return Optional.ofNullable(
                    this.client
                            .get()
                            .uri("clinic-api/pets/{petId}", petId)
                            .retrieve()
                            .body(Pet.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public void updatePet(Integer petId, String name, Integer clientId, Integer typeId, Integer breedId, Integer genderId, LocalDate birthday, Integer labelId) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/pets/{petId}", petId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdatePetPayload(name, clientId, typeId, breedId, genderId, birthday, labelId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public void deletePet(Integer petId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/pets/{petId}", petId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
