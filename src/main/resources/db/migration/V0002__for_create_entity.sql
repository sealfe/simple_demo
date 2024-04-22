create table entity
(
    id              varchar(255) not null
        primary key,
    name            varchar(255) not null,
    creator_id      varchar(255) not null,
    updater_id      varchar(255) not null,
    created_at      timestamp    not null,
    created_at_zone varchar(255) not null,
    updated_at      timestamp    not null,
    updated_at_zone varchar(255) not null,
    is_deleted      boolean      not null
);