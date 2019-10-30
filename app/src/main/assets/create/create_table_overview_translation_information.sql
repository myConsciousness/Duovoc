create table if not exists overview_translation_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	id TEXT NOT NULL UNIQUE,
	header TEXT,
	translation TEXT NOT NULL,
	modified_datetime TEXT NOT NULL
)
;

commit;