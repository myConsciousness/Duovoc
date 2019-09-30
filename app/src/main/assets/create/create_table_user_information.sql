create table if not exists user_information(
	user_id TEXT PRIMARY KEY,
	login_name TEXT,
	login_password TEXT,
	user_name TEXT
)
;

commit;