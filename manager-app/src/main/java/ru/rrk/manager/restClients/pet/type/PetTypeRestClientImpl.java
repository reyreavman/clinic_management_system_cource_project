package ru.rrk.manager.restClients.pet.type;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.pet.types.payload.NewPetTypePayload;
import ru.rrk.manager.controller.pet.types.payload.UpdatePetTypePayload;
import ru.rrk.manager.entity.pet.PetType;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class PetTypeRestClientImpl implements PetTypeRestClient {
    private static final ParameterizedTypeReference<List<PetType>> PETTYPE_TYPE_REFERENCE = new ParameterizedTypeReference<List<PetType>>() {
    };
    private final RestClient client;

    @Override
    public List<PetType> findAllTypes(String filter) {
        return this.client
                .get()
                .uri("/clinic-api/pets/types?filter={filter}", filter)
                .retrieve()
                .body(PETTYPE_TYPE_REFERENCE);
    }

    @Override
    public PetType createType(String name) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/pets/types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewPetTypePayload(name))
                    .retrieve().body(PetType.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<PetType> findType(int typeId) {
        try {
            return Optional.ofNullable(this.client
                    .get().uri("/clinic-api/pets/types/{typeId}", typeId)
                    .retrieve().body(PetType.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateType(int typeId, String name) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/pets/types/{typeId}", typeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdatePetTypePayload(name))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteType(int typeId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/pets/types/{typeId}", typeId)
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
