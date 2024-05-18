alter table clinic.t_appointment_result add column if not exists c_current_app_id integer not null references clinic.t_appointment(id);
