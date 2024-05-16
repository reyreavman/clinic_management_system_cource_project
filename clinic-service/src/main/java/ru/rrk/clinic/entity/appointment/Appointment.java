package ru.rrk.clinic.entity.appointment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rrk.clinic.entity.checkup.Checkup;
import ru.rrk.clinic.entity.pet.Pet;
import ru.rrk.clinic.entity.vet.Vet;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "clinic", name = "t_appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_pet_id")
    @NotNull
    private Pet pet;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_vet_id")
    @NotNull
    private Vet vet;

    @Column(name = "c_app_date")
    @NotNull
    private LocalDate date;

    @Column(name = "c_app_time")
    @NotNull
    private LocalTime time;

    @Column(name = "c_description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_checkup_id")
    private Checkup checkup;
}
