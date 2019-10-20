create table if not exists current_user_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	user_id TEXT NOT NULL UNIQUE,
	language TEXT NOT NULL,
	from_language TEXT NOT NULL,
	modified_datetime TEXT NOT NULL
)
;

commit;
