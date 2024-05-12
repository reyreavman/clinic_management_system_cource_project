package ru.rrk.clinic.controller.pet.breed.payload;

import jakarta.validation.constraints.NotNull;

public record NewBreedPayload(
        @NotNull
        String name,
        @NotNull
        Integer typeId
) {
}
