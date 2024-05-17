package ru.rrk.manager.entity;

public record Diagnosis(
        int id,
        Disease disease,
        String description
) {
}
