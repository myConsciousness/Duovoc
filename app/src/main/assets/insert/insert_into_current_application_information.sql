insert into current_application_information(
    config_name,
    config_value,
    modified_datetime
)
values(
    'uses_wifi_on_communicate',
    '0',
    datetime('now', 'localtime')
)
;

commit;