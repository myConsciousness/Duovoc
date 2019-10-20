create table if not exists supported_language_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	from_language TEXT NOT NULL UNIQUE,
	learning_language TEXT NOT NULL,
	modified_datetime TEXT NOT NULL
)
;
commit;
