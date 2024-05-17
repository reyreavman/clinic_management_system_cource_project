package ru.rrk.manager.restClients.vet.vet;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.vets.vet.payload.NewVetPayload;
import ru.rrk.manager.controller.vets.vet.payload.UpdateVetPayload;
import ru.rrk.manager.entity.vet.Vet;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class VetRestClientImpl implements VetRestClient {
    private static final ParameterizedTypeReference<List<Vet>> VETS_TYPE_REFERENCE = new ParameterizedTypeReference<List<Vet>>() {
    };
    private final RestClient client;

    @Override
    public List<Vet> findAllVets(String filter) {
        return this.client
                .get()
                .uri("clinic-api/vets?filter={filter}", filter)
                .retrieve()
                .body(VETS_TYPE_REFERENCE);
    }

    @Override
    public Vet createVet(String firstName, String lastName, Integer speciality) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/vets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewVetPayload(firstName, lastName, speciality))
                    .retrieve()
                    .body(Vet.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Vet> findVet(Integer vetId) {
        try {
            return Optional.ofNullable(
                    this.client
                            .get()
                            .uri("clinic-api/vets/{vetId}", vetId)
                            .retrieve()
                            .body(Vet.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateVet(Integer vetId, String firstName, String lastName, Integer speciality_id) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/vets/{vetId}", vetId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateVetPayload(firstName, lastName, speciality_id))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteVet(Integer vetId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/vets/{vetId}", vetId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}