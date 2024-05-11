package ru.rrk.clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "clinic", name = "t_receptionist")
public class Receptionist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_first_name")
    @NotNull
    @Size(max = 100)
    private String firstName;

    @Column(name = "c_last_name")
    @NotNull
    @Size(max = 100)
    private String lastName;

}
