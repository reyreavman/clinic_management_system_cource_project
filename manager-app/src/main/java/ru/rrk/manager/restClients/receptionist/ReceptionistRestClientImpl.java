package ru.rrk.manager.restClients.receptionist;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.receptionists.payload.NewReceptionistPayload;
import ru.rrk.manager.controller.receptionists.payload.UpdateReceptionistPayload;
import ru.rrk.manager.entity.Receptionist;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class ReceptionistRestClientImpl implements ReceptionistRestClient {
    private static final ParameterizedTypeReference<List<Receptionist>> RECEPTIONIST_TYPE_REFERENCE = new ParameterizedTypeReference<List<Receptionist>>() {
    };
    private final RestClient client;

    @Override
    public List<Receptionist> findAllReceptionists() {
        return this.client
                .get()
                .uri("/clinic-api/receptionists")
                .retrieve()
                .body(RECEPTIONIST_TYPE_REFERENCE);
    }

    @Override
    public Receptionist createReceptionist(String firstName, String lastName) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/receptionists")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewReceptionistPayload(firstName, lastName))
                    .retrieve()
                    .body(Receptionist.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Receptionist> findReceptionist(int receptionistId) {
        try {
            return Optional.ofNullable(this.client
                    .get()
                    .uri("/clinic-api/receptionists/{receptionistId}", receptionistId)
                    .retrieve()
                    .body(Receptionist.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateReceptionist(int receptionistId, String firstName, String lastName) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/receptionists/{receptionistId}", receptionistId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateReceptionistPayload(firstName, lastName))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteReceptionist(int receptionistId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/receptionists/{receptionistId}", receptionistId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
