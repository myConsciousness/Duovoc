drop trigger if exists insert_trigger_master_message_information;/
drop trigger if exists insert_trigger_user_information;/
drop trigger if exists insert_trigger_overview_information;/
drop trigger if exists insert_trigger_overview_translation_information;/
drop trigger if exists insert_trigger_current_user_information;/
drop trigger if exists insert_trigger_current_application_information;/
drop trigger if exists insert_trigger_supported_language_information;/

create trigger insert_trigger_master_message_information after
insert
on  master_message_information begin
    update
        master_message_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
create trigger insert_trigger_user_information after
insert
on  user_information begin
    update
        user_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
create trigger insert_trigger_overview_information after
insert
on  overview_information begin
    update
        overview_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
create trigger insert_trigger_overview_translation_information after
insert
on  overview_translation_information begin
    update
        overview_translation_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
create trigger insert_trigger_current_user_information after
insert
on  current_user_information begin
    update
        current_user_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
create trigger insert_trigger_current_application_information after
insert
on  current_application_information begin
    update
        current_application_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
create trigger insert_trigger_supported_language_information after
insert
on  supported_language_information begin
    update
        supported_language_information
    set
        modified_datetime = datetime(
            'now',
            'localtime'
        )
;
END
;/
