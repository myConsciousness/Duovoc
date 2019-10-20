create trigger update_trigger_master_message_information after
update
on  master_message_information begin
    update
        master_message_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_user_information after
update
on  user_information begin
    update
        user_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_language_information after
update
on  language_information begin
    update
        language_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_overview_information after
update
on  overview_information begin
    update
        overview_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_overview_translation_information after
update
on  overview_translation_information begin
    update
        overview_translation_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_current_user_information after
update
on  current_user_information begin
    update
        current_user_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_current_application_information after
update
on  current_application_information begin
    update
        current_application_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;
create trigger update_trigger_supported_language_information after
update
on  supported_language_information begin
    update
        supported_language_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
    where
        row_id = old.row_id
;
END
;

commit;
