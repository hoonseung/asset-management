alter table corporation
    add constraint uk_corporation_name
        unique (name);