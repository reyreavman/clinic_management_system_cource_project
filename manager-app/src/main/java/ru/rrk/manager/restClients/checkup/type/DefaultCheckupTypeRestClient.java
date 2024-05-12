package ru.rrk.manager.restClients.checkup.type;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.checkup.type.payload.NewCheckupTypePayload;
import ru.rrk.manager.controller.checkup.type.payload.UpdateCheckupTypePayload;
import ru.rrk.manager.entity.checkup.CheckupType;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultCheckupTypeRestClient implements CheckupTypeRestClient {
    private static final ParameterizedTypeReference<List<CheckupType>> CHECKUPTYPE_TYPE_REFERENCE = new ParameterizedTypeReference<List<CheckupType>>() {
    };
    private final RestClient client;

    @Override
    public List<CheckupType> findAllTypes() {
        return this.client
                .get()
                .uri("/clinic-api/checkups/types")
                .retrieve()
                .body(CHECKUPTYPE_TYPE_REFERENCE);
    }

    @Override
    public CheckupType createType(String type) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/checkups/types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewCheckupTypePayload(type))
                    .retrieve()
                    .body(CheckupType.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<CheckupType> findType(int typeId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/checkups/types/{typeId}", typeId)
                    .retrieve()
                    .body(CheckupType.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateType(int typeId, String type) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/checkups/types/{typeId}", typeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateCheckupTypePayload(type))
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
                    .uri("/clinic-api/checkups/types/{typeId}", typeId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
