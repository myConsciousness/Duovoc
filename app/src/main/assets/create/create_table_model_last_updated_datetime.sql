create table if not exists model_last_updated_datetime_information(
    model_physical_name TEXT NOT NULL PRIMARY KEY,
    last_updated_datetime TEXT NOT NULL,
    last_updated_date TEXT NOT NULL,
    last_updated_time TEXT NOT NULL
)
;

commit;