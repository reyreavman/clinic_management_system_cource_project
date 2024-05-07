package ru.rrk.manager.restClients.label;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.entity.Label;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class LabelRestClientImpl implements LabelRestClient {
    private static final ParameterizedTypeReference<List<Label>> LABEL_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient client;

    @Override
    public List<Label> findAllLabels() {
        return this.client
                .get()
                .uri("/clinic-api/labels")
                .retrieve()
                .body(LABEL_TYPE_REFERENCE);
    }

    @Override
    public Label createLabel(String value, Date date) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/labels")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewLabelPayload(value, date))
                    .retrieve()
                    .body(Label.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Label> findLabel(int labelId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/labels/{labelId}", labelId)
                    .retrieve()
                    .body(Label.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateLabel(int labelId, String value, Date date) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/labels/{labelId}", labelId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateLabelPayload(value, date))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteLabel(int labelId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/labels/{labelId}", labelId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
