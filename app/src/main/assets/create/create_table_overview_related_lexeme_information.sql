create table if not exists overview_related_lexeme_information(
    row_id INTEGER NOT NULL PRIMARY KEY,
    lexeme_id TEXT NOT NULL UNIQUE,
    overview_id TEXT NOT NULL UNIQUE,
    word TEXT NOT NULL,
    lesson_name TEXT NOT NULL,
    modified_datetime TEXT NOT NULL
)
;

commit;