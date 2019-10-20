create table if not exists user_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	user_id TEXT UNIQUE,
	login_name TEXT,
	login_password TEXT,
	user_name TEXT,
	modified_datetime TEXT NOT NULL
)
;

commit;