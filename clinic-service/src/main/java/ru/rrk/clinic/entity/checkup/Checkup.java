package ru.rrk.clinic.entity.checkup;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rrk.clinic.entity.pet.Pet;
import ru.rrk.clinic.entity.vet.Vet;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(schema = "clinic", name = "t_checkup")
public class Checkup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_checkup_date")
    @NotNull
    private LocalDate date;

    @Column(name = "c_checkup_time")
    @NotNull
    private LocalTime time;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_pet_id")
    @NotNull
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_vet_id")
    @NotNull
    private Vet vet;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_type_id")
    @NotNull
    private CheckupType checkupType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_state_id")
    @NotNull
    private CheckupState checkupState;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_result_id")
    @NotNull
    private CheckupResult checkupResult;
}
