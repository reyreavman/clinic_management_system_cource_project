package ru.rrk.common.restClient.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.receptionist.controller.pet.payload.label.NewLabelPayload;
import ru.rrk.users.receptionist.controller.pet.payload.label.UpdateLabelPayload;
import ru.rrk.common.dto.pet.Label;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class LabelRestClient {
    private static final ParameterizedTypeReference<List<Label>> LABEL_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient client;

    public List<Label> findAllLabels() {
        return this.client
                .get()
                .uri("/clinic-api/pets/labels")
                .retrieve()
                .body(LABEL_TYPE_REFERENCE);
    }

    public Label createLabel(String value, String date) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/pets/labels")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewLabelPayload(value, date))
                    .retrieve()
                    .body(Label.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    public Optional<Label> findLabel(int labelId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/pets/labels/{labelId}", labelId)
                    .retrieve()
                    .body(Label.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public void updateLabel(int labelId, String value, String date) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/pets/labels/{labelId}", labelId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateLabelPayload(value, date))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public void deleteLabel(int labelId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/pets/labels/{labelId}", labelId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
