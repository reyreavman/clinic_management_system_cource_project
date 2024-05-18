alter table clinic.t_appointment_result alter column c_next_app_id drop not null;
alter table clinic.t_appointment_result drop column c_app_id;

