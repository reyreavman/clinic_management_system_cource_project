package ru.rrk.clinic.entity.pet;

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
@Table(schema = "clinic", name = "t_breed")
public class PetBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_type_id")
    @NotNull
    private PetType type;

    @Column(name = "c_name")
    @NotNull
    @Size(max = 100)
    private String name;
}
