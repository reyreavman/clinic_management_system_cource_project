package ru.rrk.common.restClient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.users.receptionist.controller.BadRequestException;
import ru.rrk.users.receptionist.controller.client.payload.NewClientPayload;
import ru.rrk.users.receptionist.controller.client.payload.UpdateClientPayload;
import ru.rrk.common.dto.Client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientRestClient {
    private static final ParameterizedTypeReference<List<Client>> CLIENT_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient restClient;

    public List<Client> findAllClients(String filter) {
        return this.restClient
                .get()
                .uri("/clinic-api/clients?filter={filter}", filter)
                .retrieve()
                .body(CLIENT_TYPE_REFERENCE);
    }

    public Client createClient(String firstName, String lastName, String phoneNumber, String email) {
        try {
            return this.restClient
                    .post()
                    .uri("/clinic-api/clients")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewClientPayload(firstName, lastName, phoneNumber, email))
                    .retrieve()
                    .body(Client.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    public Optional<Client> findClient(int clientId) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/clinic-api/clients/{clientId}", clientId)
                    .retrieve()
                    .body(Client.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    public void updateClient(int clientId, String firstName, String lastName, String phoneNumber, String email) {
        try {
            this.restClient
                    .patch()
                    .uri("/clinic-api/clients/{clientId}", clientId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateClientPayload(firstName, lastName, phoneNumber, email))
                    .retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public void deleteClient(int clientId) {
        try {
            this.restClient
                    .delete()
                    .uri("/clinic-api/clients/{clientId}", clientId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
