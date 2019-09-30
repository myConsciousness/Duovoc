create table if not exists current_application_information(
    config_name TEXT NOT NULL PRIMARY KEY,
    config_value TEXT NOT NULL
)
;

commit;