create table if not exists master_message_information(
	message_id TEXT not null primary key,
	message TEXT not null,
	language_kind TEXT not null,
	error_kind TEXT not null
)
;

commit;