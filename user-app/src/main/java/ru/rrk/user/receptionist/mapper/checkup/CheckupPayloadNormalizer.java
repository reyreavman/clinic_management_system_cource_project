package ru.rrk.user.receptionist.mapper.checkup;

import lombok.RequiredArgsConstructor;
import ru.rrk.user.receptionist.controller.payload.NewCheckupPayload;
import ru.rrk.user.receptionist.restClient.checkup.CheckupResultRestClient;
import ru.rrk.user.receptionist.restClient.checkup.CheckupStateRestClient;

@RequiredArgsConstructor
public class CheckupPayloadNormalizer {
    private final CheckupStateRestClient stateRestClient;
    private final CheckupResultRestClient resultRestClient;

    public NewCheckupPayload normalizePayload(NewCheckupPayload payload) {
        if (payload.getStateId() == null) {
            this.stateRestClient.findAllStates().stream().filter(state -> state.state().toLowerCase().equals("ожидается")).findFirst().ifPresent(state -> payload.setStateId(state.id()));
        }
        if (payload.getResultId() == null) {
            payload.setResultId(this.resultRestClient.createResult(null).id());
        }
        return payload;
    }
}
