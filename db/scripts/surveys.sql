create table if not exists survey_categories
(
    id          bigserial
        primary key,
    name        varchar(255)                           not null,
    description varchar(255)                           not null,
    created     timestamp with time zone default now() not null,
    created_by  bigint                                 not null
);

create table if not exists surveys
(
    id                 serial
        primary key,
    name               text,
    start_date         timestamp with time zone               not null,
    end_date           timestamp with time zone               not null,
    is_active          boolean                  default true  not null,
    created_at         timestamp with time zone default now() not null,
    updated_at         timestamp with time zone default now() not null,
    created_by         bigint                                 not null,
    participants_count integer                  default 0     not null
);

create index if not exists name_ts_idx
    on surveys using gin (to_tsvector('simple'::regconfig, immutable_unaccent(COALESCE(name, ''::text))));

create table if not exists survey_responses
(
    id           bigserial
        primary key,
    survey_id    bigint                                                                        not null,
    user_id      bigint                                                                        not null,
    submitted_at timestamp with time zone default now()                                        not null,
    category_id  bigint                                                                        not null,
    rating       integer                                                                       not null,
    comment      text,
    fullname     varchar(255)                                                                  not null,
    address      text                                                                          not null,
    phone_number varchar(50)                                                                   not null,
    gender       varchar(10)                                                                   not null
);

