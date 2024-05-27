package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.entity.vet.Vet;

import java.util.Optional;

@RequiredArgsConstructor
public class VetRestClient {
    private final RestClient restClient;

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
