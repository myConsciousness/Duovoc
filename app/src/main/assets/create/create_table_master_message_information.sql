create table if not exists master_message_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	message_id TEXT NOT NULL UNIQUE,
	message TEXT NOT NULL,
	language_kind TEXT NOT NULL,
	error_kind TEXT NOT NULL,
	modified_datetime TEXT NOT NULL
)
;

commit;