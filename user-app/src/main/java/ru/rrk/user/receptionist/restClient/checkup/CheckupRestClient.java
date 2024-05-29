package ru.rrk.user.receptionist.restClient.checkup;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.user.receptionist.controller.BadRequestException;
import ru.rrk.user.receptionist.controller.payload.NewCheckupDetailsPayload;
import ru.rrk.user.receptionist.dto.checkup.Checkup;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupRestClient {
    private static final ParameterizedTypeReference<List<Checkup>> CHECKUP_TYPE_REFERENCE = new ParameterizedTypeReference<List<Checkup>>() {
    };
    private final RestClient client;

    public List<Checkup> findAllCheckups() {
        return this.client
                .get()
                .uri("clinic-api/checkups")
                .retrieve()
                .body(CHECKUP_TYPE_REFERENCE);
    }

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

    public Checkup createCheckup(LocalDate date, LocalTime time, Integer petId, Integer vetId, Integer checkupTypeId, Integer checkupStateId, Integer checkupResultId) {
        try {
            return this.client
                    .post()
                    .uri("clinic-api/checkups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewCheckupDetailsPayload(date, time, petId, vetId, checkupTypeId, checkupStateId, checkupResultId))
                    .retrieve()
                    .body(Checkup.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }
}
