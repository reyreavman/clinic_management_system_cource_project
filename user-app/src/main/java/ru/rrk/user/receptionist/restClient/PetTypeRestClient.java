package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.dto.pet.Type;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PetTypeRestClient {
    private static final ParameterizedTypeReference<List<Type>> PETTYPE_TYPE_REFERENCE = new ParameterizedTypeReference<List<Type>>() {
    };
    private final RestClient restClient;

    public List<Type> findAllTypes() {
        return this.restClient
                .get()
                .uri("/clinic-api/pets/types")
                .retrieve()
                .body(PETTYPE_TYPE_REFERENCE);
    }

    public Optional<Type> findType(int typeId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get().uri("/clinic-api/pets/types/{typeId}", typeId)
                    .retrieve().body(Type.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
