package ru.rrk.manager.restClients.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.clients.payload.NewClientPayload;
import ru.rrk.manager.controller.clients.payload.UpdateClientPayload;
import ru.rrk.manager.entity.Client;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientRestClientImpl implements ClientRestClient {
    private static final ParameterizedTypeReference<List<Client>> CLIENT_TYPE_REFERENCE = new ParameterizedTypeReference<>() {};
    private final RestClient client;

    @Override
    public List<Client> findAllClients(String filter) {
        return this.client
                .get()
                .uri("/clinic-api/clients?filter={filter}", filter)
                .retrieve()
                .body(CLIENT_TYPE_REFERENCE);
    }

    @Override
    public Client createClient(String firstName, String lastName, String phoneNumber, String email) {
        try {
            return this.client
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

    @Override
    public Optional<Client> findClient(int clientId) {
        try {
            return Optional.ofNullable(this.client.get()
                    .uri("/clinic-api/clients/{clientId}", clientId)
                    .retrieve()
                    .body(Client.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateClient(int clientId, String firstName, String lastName, String phoneNumber, String email) {
        try {
            this.client
                    .patch()
                    .uri("/clinic-api/clients/{clientId}", clientId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateClientPayload(firstName, lastName, phoneNumber, email))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteClient(int clientId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/clients/{clientId}", clientId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
