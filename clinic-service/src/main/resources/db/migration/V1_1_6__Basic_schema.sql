create table clinic.t_icd_catalogue
(
    id              serial primary key,
    c_code          integer unique not null,
    c_description   varchar(255)
);