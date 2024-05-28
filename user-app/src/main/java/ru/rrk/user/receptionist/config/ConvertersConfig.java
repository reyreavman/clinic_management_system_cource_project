package ru.rrk.user.receptionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rrk.user.receptionist.mapper.*;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentViewPrimaryConverter;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentViewSummaryConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupViewPrimaryConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupViewSummaryConverter;

@Configuration
public class ConvertersConfig {
    @Bean
    public AppointmentViewSummaryConverter appointmentViewPrimaryConverter() {
        return new AppointmentViewSummaryConverter();
    }

    @Bean
    public AppointmentViewPrimaryConverter appointmentViewSummaryConverter() {
        return new AppointmentViewPrimaryConverter();
    }

    @Bean
    public CheckupViewSummaryConverter checkupViewSummaryConverter() {
        return new CheckupViewSummaryConverter();
    }

    @Bean
    public CheckupViewPrimaryConverter checkupViewPrimaryConverter() {
        return new CheckupViewPrimaryConverter();
    }

    @Bean
    public PetViewSummaryConverter petViewSummaryConverter() {
        return new PetViewSummaryConverter();
    }

    @Bean
    public VetViewSummaryConverter vetViewSummaryConverter() {
        return new VetViewSummaryConverter();
    }
}
