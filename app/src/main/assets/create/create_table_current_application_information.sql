create table if not exists current_application_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	config_name TEXT NOT NULL UNIQUE,
	config_value TEXT NOT NULL,
	modified_datetime TEXT NOT NULL
)
;

commit;