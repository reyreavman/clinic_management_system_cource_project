package ru.rrk.manager.restClients.speciality;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.controller.specialities.payload.NewSpecialityPayload;
import ru.rrk.manager.controller.specialities.payload.UpdateSpecialityPayload;
import ru.rrk.manager.entity.Speciality;
import ru.rrk.manager.restClients.BadRequestException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class SpecialityRestClientImpl implements SpecialityRestClient {
    private static final ParameterizedTypeReference<List<Speciality>> SPECIALITY_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };
    private final RestClient client;

    @Override
    public List<Speciality> findAllSpecialities(String filter) {
        return this.client
                .get()
                .uri("/clinic-api/specialities?filter={filter}", filter)
                .retrieve()
                .body(SPECIALITY_TYPE_REFERENCE);
    }

    @Override
    public Speciality createSpeciality(String name) {
        try {
            return this.client
                    .post()
                    .uri("/clinic-api/specialities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewSpecialityPayload(name))
                    .retrieve()
                    .body(Speciality.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail detail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) detail.getProperties().get("errors"));
        }
    }

    @Override
    public Optional<Speciality> findSpeciality(int specialityId) {
        try {
            return Optional.ofNullable(this.client.get().uri("/clinic-api/specialities/{specialityId}", specialityId).retrieve().body(Speciality.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateSpeciality(int specialityId, String name) {
        try {
            this.client.patch().uri("/clinic-api/specialities/{specialityId}", specialityId).contentType(MediaType.APPLICATION_JSON).body(new UpdateSpecialityPayload(name)).retrieve().toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteSpeciality(int specialityId) {
        try {
            this.client
                    .delete()
                    .uri("/clinic-api/specialities/{specialityId}", specialityId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
