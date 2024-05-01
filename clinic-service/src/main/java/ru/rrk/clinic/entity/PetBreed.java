package ru.rrk.clinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "clinic", name = "t_breed")
public class PetBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinTable(schema = "clinic", name = "t_type",
    joinColumns = @JoinColumn(name = "c_name"))
    private PetType type;

    @Column(name = "c_name")
    private String name;
}
