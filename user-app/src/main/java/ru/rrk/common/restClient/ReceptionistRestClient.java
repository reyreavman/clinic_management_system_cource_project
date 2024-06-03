package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.common.dto.Receptionist;

import java.util.Optional;

@RequiredArgsConstructor
public class ReceptionistRestClient {
    private final RestClient restClient;

    public Optional<Receptionist> findReceptionist(int receptionistId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/clinic-api/receptionists/{receptionistId}", receptionistId)
                    .retrieve()
                    .body(Receptionist.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
