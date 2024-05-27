package ru.rrk.user.receptionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rrk.user.receptionist.converter.AppointmentDTOConverter;

@Configuration
public class ConvertersConfig {
    @Bean
    public AppointmentDTOConverter appointmentDTOConverter() {
        return new AppointmentDTOConverter();
    }
}
