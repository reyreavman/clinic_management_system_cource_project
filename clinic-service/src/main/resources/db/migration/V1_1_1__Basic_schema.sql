create schema if not exists clinic;

create table clinic.t_client
(
    id              serial primary key,
    c_first_name    varchar(50) not null check (length(trim(c_first_name)) >= 2),
    c_last_name     varchar(50),
    c_phone_number  varchar(20) not null check (length(trim(c_last_name)) >= 10),
    c_email         varchar(255)
);