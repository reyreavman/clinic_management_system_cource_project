drop table if exists clinic.c_appointments_metadata;
drop table if exists clinic.t_checkup_meta;

create table if not exists clinic.t_diagnoses(
    id              serial primary key,
    disease_id      integer references clinic.t_icd_catalogue (id),
    description     varchar
);

alter table clinic.t_appointment_result add column if not exists c_diagnosis_id integer references clinic.t_diagnoses(id);
