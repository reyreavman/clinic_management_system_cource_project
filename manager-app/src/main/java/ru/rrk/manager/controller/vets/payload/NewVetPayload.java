package ru.rrk.manager.controller.vets.payload;

import ru.rrk.manager.entity.Speciality;

public record NewVetPayload(String firstName, String lastName, Speciality speciality) {
}
