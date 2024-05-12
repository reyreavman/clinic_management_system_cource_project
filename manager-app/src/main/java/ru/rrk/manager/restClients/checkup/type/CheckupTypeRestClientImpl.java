package ru.rrk.manager.restClients.checkup.type;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import ru.rrk.manager.entity.checkup.CheckupType;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckupTypeRestClientImpl implements CheckupTypeRestClient {
    private static final ParameterizedTypeReference<List<CheckupType>> CHECKUPTYPE_TYPE_REFERENCE = new ParameterizedTypeReference<List<CheckupType>>() {
    };
    private final RestClient client;

    @Override
    public List<CheckupType> findAllTypes() {
        return null;
    }

    @Override
    public CheckupType createType(String type) {
        return null;
    }

    @Override
    public Optional<CheckupType> findType(int typeId) {
        return Optional.empty();
    }

    @Override
    public void updateType(int typeId, String type) {

    }

    @Override
    public void deleteType(int typeId) {

    }
}
