package ru.rrk.manager.controller.labels.payload;

import java.util.Date;

public record NewLabelPayload(String value, Date date) {
}
