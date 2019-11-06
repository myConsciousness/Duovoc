create table if not exists overview_bookmark_information(
    row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
    overview_id TEXT NOT NULL UNIQUE,
    modified_datetime TEXT NOT NULL
)
;

commit;
