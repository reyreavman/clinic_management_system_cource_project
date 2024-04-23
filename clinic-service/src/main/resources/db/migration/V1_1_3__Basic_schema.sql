create schema if not exists clinic;

create table clinic.t_speciality
(
    id                  serial primary key,
    name                varchar(100) unique not null
);

create table clinic.t_vet
(
    id                  serial primary key,
    c_first_name        varchar(100) not null,
    c_second_name       varchar(100) not null,
    c_speciality_id     integer references clinic.t_speciality (id) not null
);

create table clinic.t_appointment
(
    id                  serial primary key,
    c_pet_id            integer references clinic.t_pet (id) not null,
    c_app_date          date not null,
    c_app_time          time not null,
    c_vet_id            integer references clinic.t_vet (id) not null,
    c_discription       varchar(255)
);

create table clinic.t_receptionist
(
    id                  serial primary key,
    c_first_name        varchar(100) not null,
    c_last_name         varchar(100) not null
);

create table clinic.t_appointment_metadata
(
    id                  serial primary key,
    c_receptionist_id   integer references clinic.t_receptionist (id) not null,
    c_appointment_id    integer references clinic.t_appointment (id) not null
);

create table clinic.t_appointment_result_state
(
    id                  serial primary key,
    c_state_name        varchar(255) unique not null
);

create table clinic.t_appointment_result
(
    id                  serial primary key,
    c_app_id            integer references clinic.t_appointment (id) not null,
    c_result_state_id   integer references clinic.t_appointment_result_state (id) not null,
    c_advice            varchar(255),
    c_prescription      varchar(255),
    c_next_app_id       integer references clinic.t_appointment (id) not null
);


create table clinic.t_checkup_type
(
    id                  serial primary key,
    c_checkup_type      varchar(255) unique not null
);

create table clinic.t_checkup_state
(
    id                  serial primary key,
    c_state_name        varchar(255) unique not null
);

create table clinic.t_checkup_result
(
    id                  serial primary key,
    c_description       varchar(255)
);

create table clinic.t_checkup
(
    id                  serial primary key,
    c_checkup_date      date not null,
    c_checkup_time      time not null,
    c_pet_id            integer references clinic.t_pet (id) not null,
    c_vet_id            integer references clinic.t_vet (id) not null,
    c_type_id           integer references clinic.t_checkup_type (id) not null,
    c_state_id          integer references clinic.t_checkup_state (id) not null
);

create table clinic.t_checkup_meta
(
    id                  serial primary key,
    c_checkup_id        integer references clinic.t_checkup (id) not null,
    c_checkup_res_id    integer references clinic.t_checkup_result (id) not null
)