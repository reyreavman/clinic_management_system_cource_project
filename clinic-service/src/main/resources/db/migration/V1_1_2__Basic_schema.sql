create schema if not exists clinic;

create table clinic.t_type
(
    id              serial primary key,
    c_name          varchar(100) unique not null
);

create table clinic.t_breed
(
    id              serial primary key,
    c_type_id       integer references clinic.t_type (id) not null,
    c_name          varchar(100) not null
);

create table clinic.t_gender
(
    id              serial primary key,
    c_gender        varchar(10) unique
);

create table clinic.t_label
(
    id              serial primary key,
    c_value         varchar(10) unique,
    c_date_info     date
);

create table clinic.t_pet
(
    id              serial primary key,
    c_client_id     integer references clinic.t_client (id) not null,
    c_pet_name      varchar(50) not null,
    c_type_id       integer references clinic.t_type (id) not null,
    c_breed_id      integer references clinic.t_breed (id) not null,
    c_gender_id     integer references clinic.t_gender (id) not null,
    c_birth_date    date,
    c_label_id      integer references clinic.t_label (id)
);