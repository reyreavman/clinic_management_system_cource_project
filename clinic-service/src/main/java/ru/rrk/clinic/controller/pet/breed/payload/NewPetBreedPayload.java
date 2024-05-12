package ru.rrk.clinic.controller.pet.breed.payload;

import jakarta.validation.constraints.NotNull;

public record NewPetBreedPayload(
        @NotNull
        String name,
        @NotNull
        Integer typeId
) {
}
