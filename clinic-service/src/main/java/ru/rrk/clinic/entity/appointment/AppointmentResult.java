package ru.rrk.clinic.entity.appointment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rrk.clinic.entity.Diagnosis;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = "clinic", name = "t_appointment_result")
public class AppointmentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_current_app_id")
    @NotNull
    private Appointment currentAppointment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_next_app_id")
    private Appointment nextAppointment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "c_result_state_id")
    @NotNull
    private AppointmentResultState state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_diagnosis_id")
    private Diagnosis diagnosis;

    @Column(name = "c_advice")
    private String advice;

    @Column(name = "c_prescription")
    private String prescription;
}
