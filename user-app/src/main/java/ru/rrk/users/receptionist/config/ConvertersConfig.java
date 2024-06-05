package ru.rrk.users.receptionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rrk.common.mapper.appointment.AppointmentListViewConverter;
import ru.rrk.common.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.common.mapper.appointment.AppointmentResultSummaryViewConverter;
import ru.rrk.common.mapper.appointment.AppointmentSummaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.common.mapper.checkup.CheckupSummaryViewConverter;
import ru.rrk.common.mapper.pet.PetViewPrimaryConverter;
import ru.rrk.common.mapper.pet.PetViewSummaryConverter;
import ru.rrk.common.mapper.vet.VetViewConverter;

@Configuration
public class ConvertersConfig {
    @Bean
    public AppointmentSummaryViewConverter appointmentViewPrimaryConverter() {
        return new AppointmentSummaryViewConverter();
    }

    @Bean
    public AppointmentPrimaryViewConverter appointmentViewSummaryConverter() {
        return new AppointmentPrimaryViewConverter();
    }

    @Bean
    public CheckupSummaryViewConverter checkupViewSummaryConverter() {
        return new CheckupSummaryViewConverter();
    }

    @Bean
    public CheckupPrimaryViewConverter checkupViewPrimaryConverter() {
        return new CheckupPrimaryViewConverter();
    }

    @Bean
    public PetViewSummaryConverter petViewSummaryConverter() {
        return new PetViewSummaryConverter();
    }

    @Bean
    public VetViewConverter vetViewSummaryConverter() {
        return new VetViewConverter();
    }

    @Bean
    public PetViewPrimaryConverter petViewPrimaryConverter() {
        return new PetViewPrimaryConverter();
    }

    @Bean
    public AppointmentListViewConverter appointmentListViewConverter() {
        return new AppointmentListViewConverter();
    }

    @Bean
    public AppointmentResultSummaryViewConverter appointmentResultSummaryViewConverter() {
        return new AppointmentResultSummaryViewConverter();
    }
}
