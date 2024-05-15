alter table if exists clinic.t_appointment add column if not exists c_checkup_id integer references clinic.t_checkup(id);
alter table clinic.t_appointment rename column c_discription to c_description;