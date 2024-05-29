package ru.rrk.user.receptionist.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.dto.pet.Gender;

import java.util.List;

@RequiredArgsConstructor
public class GenderRestClient {
    private static final ParameterizedTypeReference<List<Gender>> GENDER_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient restClient;

    public List<Gender> findAllGenders() {
        return this.restClient
                .get()
                .uri("/clinic-api/genders")
                .retrieve()
                .body(GENDER_TYPE_REFERENCE);
    }
}
