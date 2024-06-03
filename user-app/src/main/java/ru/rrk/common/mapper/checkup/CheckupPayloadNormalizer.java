package ru.rrk.common.mapper.checkup;

import lombok.RequiredArgsConstructor;
import ru.rrk.users.receptionist.controller.checkup.payload.NewCheckupDetailsPayload;
import ru.rrk.users.receptionist.controller.checkup.payload.NewCheckupSummaryPayload;
import ru.rrk.common.dto.checkup.CheckupState;
import ru.rrk.common.restClient.checkup.CheckupResultRestClient;
import ru.rrk.common.restClient.checkup.CheckupStateRestClient;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class CheckupPayloadNormalizer {
    private final CheckupStateRestClient stateRestClient;
    private final CheckupResultRestClient resultRestClient;

    public NewCheckupDetailsPayload normalizePayload(NewCheckupSummaryPayload payload) {
        Integer currentStateId = this.stateRestClient.findAllStates().stream().filter(state -> state.state().equalsIgnoreCase("ожидается")).findFirst().map(CheckupState::id).orElseThrow(NoSuchElementException::new);
        Integer currentResultId = this.resultRestClient.createResult(null).id();
        return new NewCheckupDetailsPayload(payload.date(), payload.time(), payload.petId(), payload.vetId(), payload.typeId(), currentStateId, currentResultId);
    }
}
