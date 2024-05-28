package ru.rrk.user.receptionist.controller.payload;

import org.springframework.http.StreamingHttpOutputMessage;

public record NewCheckupResultPayload(String description) {
}
