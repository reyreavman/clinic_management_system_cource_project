package ru.rrk.common.dto;

public record Diagnosis(
        int id,
        Disease disease,
        String description
) {
}
