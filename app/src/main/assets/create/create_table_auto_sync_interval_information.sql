create table if not exists auto_sync_interval_information(
    row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
    user_id TEXT NOT NULL UNIQUE,
    activated_auto_sync_overview TEXT NOT NULL,
    overview_auto_sync_interval INTEGER NOT NULL,
    activated_auto_sync_hint TEXT NOT NULL,
    hint_auto_sync_interval INTEGER NOT NULL,
    modified_datetime TEXT NOT NULL
)
;

commit;
