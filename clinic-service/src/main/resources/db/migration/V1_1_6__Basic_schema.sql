create table clinic.icd_catalogue if not exists (
    id              serial primary key,
    c_code          integer unique not null,
    c_description   varchar(255)
);