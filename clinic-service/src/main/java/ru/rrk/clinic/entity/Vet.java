package ru.rrk.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "clinic", name = "t_vet")
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_first_name")
    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    @Column(name = "c_last_name")
    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    @OneToMany
    @JoinColumn(table = "clinic.t_speciality", name = "speciality_id")
    private Set<Speciality> specialitySet;
}
