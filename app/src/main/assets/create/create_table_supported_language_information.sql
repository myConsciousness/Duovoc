create table if not exists supported_language_information(
    from_language TEXT NOT NULL PRIMARY KEY,
    learning_language TEXT NOT NULL
)
;
commit;
