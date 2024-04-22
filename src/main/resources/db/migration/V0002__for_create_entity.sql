create table entity
(
    id              varchar(255) not null
        primary key,
    name            varchar(255) null,
    creator_id      varchar(255) null,
    updater_id      varchar(255) null,
    created_at      timestamp    null,
    created_at_zone varchar(255) null,
    updated_at      timestamp    null,
    updated_at_zone varchar(255) null,
    is_deleted      boolean      null
);