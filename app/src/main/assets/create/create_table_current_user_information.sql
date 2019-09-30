create table if not exists current_user_information(
    user_id TEXT NOT NULL PRIMARY KEY,
    language TEXT NOT NULL,
    from_language TEXT NOT NULL
)
;

commit;
