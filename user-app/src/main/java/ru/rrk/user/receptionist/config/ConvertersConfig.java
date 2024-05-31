package ru.rrk.user.receptionist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentListViewConverter;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentPrimaryViewConverter;
import ru.rrk.user.receptionist.mapper.appointment.AppointmentSummaryViewConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupPrimaryViewConverter;
import ru.rrk.user.receptionist.mapper.checkup.CheckupSummaryViewConverter;
import ru.rrk.user.receptionist.mapper.pet.PetViewPrimaryConverter;
import ru.rrk.user.receptionist.mapper.pet.PetViewSummaryConverter;
import ru.rrk.user.receptionist.mapper.vet.VetSummaryViewConverter;

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
    public VetSummaryViewConverter vetViewSummaryConverter() {
        return new VetSummaryViewConverter();
    }

    @Bean
    public PetViewPrimaryConverter petViewPrimaryConverter() {
        return new PetViewPrimaryConverter();
    }

    @Bean
    public AppointmentListViewConverter appointmentListViewConverter() {
        return new AppointmentListViewConverter();
    }
}
