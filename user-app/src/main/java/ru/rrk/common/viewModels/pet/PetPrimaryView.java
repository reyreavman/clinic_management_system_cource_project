package ru.rrk.common.viewModels.pet;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PetPrimaryView(
        int id,
        String name,
        int clientId,
        String clientFullName,
        String type,
        String breed,
        LocalDate birthday,
        LocalDate labelDate,
        String labelValue) {
}
