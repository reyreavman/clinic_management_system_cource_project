package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.entity.checkup.Checkup;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupRestClient {
    private static final ParameterizedTypeReference<List<Checkup>> CHECKUP_TYPE_REFERENCE = new ParameterizedTypeReference<List<Checkup>>() {
    };
    private final RestClient client;

    public List<Checkup> findAllCheckups() {
        return this.client
                .get()
                .uri("clinic-api/checkups")
                .retrieve()
                .body(CHECKUP_TYPE_REFERENCE);
    }

    public Optional<Checkup> findCheckup(int checkupId) {
        try {
            return Optional.ofNullable(
                    this.client
                            .get()
                            .uri("clinic-api/checkups/{checkupId}", checkupId)
                            .retrieve()
                            .body(Checkup.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }
}
