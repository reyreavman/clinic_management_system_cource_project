package ru.rrk.clinic.entity.pet;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rrk.clinic.entity.Client;
import ru.rrk.clinic.entity.Gender;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "clinic", name = "t_pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_pet_name")
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_client_id")
    @NotNull
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_type_id")
    @NotNull
    private PetType type;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_breed_id")
    @NotNull
    private PetBreed breed;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_gender_id")
    @NotNull
    private Gender gender;

    @Column(name = "c_birth_date")
    @Nullable
    private LocalDate birthDay;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_label_id")
    @Nullable
    private Label label;
}