package ru.rrk.manager.restClients.gender;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.genders.payload.NewGenderPayload;
import ru.rrk.manager.controller.genders.payload.UpdateGenderPayload;
import ru.rrk.manager.entity.Gender;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class GenderRestClientImpl implements GenderRestClient {
    private static final ParameterizedTypeReference<List<Gender>> GENDER_TYPE_REFERENCE = new ParameterizedTypeReference<List<Gender>>() {
    };
    private final RestClient client;

    @Override
    public List<Gender> findAllGenders() {
        return this.client
                .get()
                .uri("/clinic-api/genders")
                .retrieve()
                .body(GENDER_TYPE_REFERENCE);
    }

    @Override
    public Gender createGender(String gender) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-apu/genders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewGenderPayload(gender))
                    .retrieve()
                    .body(Gender.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Gender> findGender(int genderId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/genders/{genderId}", genderId)
                    .retrieve()
                    .body(Gender.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateGender(int genderId, String gender) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/genders/{genderId}", genderId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateGenderPayload(gender))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteGender(int genderId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/genders/{genderId}", genderId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
