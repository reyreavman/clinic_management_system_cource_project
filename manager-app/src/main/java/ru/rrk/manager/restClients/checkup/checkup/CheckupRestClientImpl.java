package ru.rrk.manager.restClients.checkup.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.checkups.checkup.payload.NewCheckupPayload;
import ru.rrk.manager.controller.checkups.checkup.payload.UpdateCheckupPayload;
import ru.rrk.manager.entity.checkup.Checkup;
import ru.rrk.manager.restClients.BadRequestException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupRestClientImpl implements CheckupRestClient {
    private static final ParameterizedTypeReference<List<Checkup>> CHECKUP_TYPE_REFERENCE = new ParameterizedTypeReference<List<Checkup>>() {
    };
    private final RestClient client;

    @Override
    public List<Checkup> findAllCheckups() {
        return this.client
                .get()
                .uri("clinic-api/checkups")
                .retrieve()
                .body(CHECKUP_TYPE_REFERENCE);
    }

    @Override
    public Checkup createCheckup(LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/checkups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewCheckupPayload(date, time, petId, vetId, checkupTypeId, checkupStateId, checkupResultId))
                    .retrieve()
                    .body(Checkup.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
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

    @Override
    public void updateCheckup(int checkupId, LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId) {
        try {
            this.client
                    .patch()
                    .uri("clinic-api/checkups/{checkupId}", checkupId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UpdateCheckupPayload(date, time, petId, vetId, checkupTypeId, checkupStateId, checkupResultId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteCheckup(int checkupId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/checkups/{checkupId}", checkupId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
