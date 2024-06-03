package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.vet.Vet;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class VetRestClient {
    private static final ParameterizedTypeReference<List<Vet>> VETS_TYPE_REFERENCE = new ParameterizedTypeReference<List<Vet>>() {
    };
    private final RestClient restClient;

    public List<Vet> findAllVets(String filter) {
        return this.restClient
                .get()
                .uri("clinic-api/vets?filter={filter}", filter)
                .retrieve()
                .body(VETS_TYPE_REFERENCE);
    }

    public Optional<Vet> findVet(int vetId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("clinic-api/vets/{vetId}", vetId)
                    .retrieve()
                    .body(Vet.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
