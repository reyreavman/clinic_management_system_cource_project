package ru.rrk.user.receptionist.dto.vet;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Vet {
    private int id;
    private String firstName;
    private String lastName;
    private Speciality speciality;
//    private String fullName; ???

    public Vet(int id, String firstName, String lastName, Speciality speciality) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.speciality = speciality;
//        this.fullName = firstName.concat(" ").concat(lastName); ???
    }
}
