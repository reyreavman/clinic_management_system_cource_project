package ru.rrk.manager.controller.vets.payload;

import ru.rrk.manager.entity.Speciality;

public record UpdateVetPayload(String firstName, String lastName, Speciality speciality) {
}
