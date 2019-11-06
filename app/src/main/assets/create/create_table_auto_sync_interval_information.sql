create table if not exists auto_sync_interval_information(
    row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
    item_name TEXT NOT NULL,
    sync_interval INTEGER NOT NULL,
    modified_datetime TEXT NOT NULL
)
;

commit;
