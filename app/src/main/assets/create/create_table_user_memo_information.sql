create table if not exists user_memo_information(
    row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
    user_id TEXT NOT NULL UNIQUE,
    overview_id TEXT NOT NULL UNIQUE,
    memo TEXT,
    modified_datetime TEXT NOT NULL
)
;

commit;