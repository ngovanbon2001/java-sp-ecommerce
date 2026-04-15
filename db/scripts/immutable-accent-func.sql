CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE OR REPLACE FUNCTION immutable_unaccent(text) RETURNS text AS $$
SELECT unaccent($1);
$$ LANGUAGE SQL IMMUTABLE;

create table if not exists complaints
(
    id         bigint                                 not null
    primary key,
    category   varchar(255)                           not null,
    level      varchar(255)                           not null,
    created    timestamp with time zone default now() not null,
    location   text                                   not null,
    title      text                                   not null,
    content    text                                   not null,
    status     varchar(255)                           not null,
    updated    timestamp with time zone default now() not null,
    created_by bigint                                 not null,
    ward_code  varchar(255)                           not null,
    lat        real                                   not null,
    lng        real                                   not null
    );

create index if not exists title_body_ts_idx
    on complaints using gin (to_tsvector('simple'::regconfig,
    immutable_unaccent((COALESCE(title, ''::text) || ' '::text) ||
    COALESCE(content, ''::text))));
