package ru.rrk.clinic.controller.vet.payload;

import ru.rrk.clinic.entity.Speciality;

public record NewVetPayload(
        String firstName,
        String lastName,
        Speciality speciality
) {
}
