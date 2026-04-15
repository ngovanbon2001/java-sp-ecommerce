create table news_categories
(
    id          bigserial primary key,
    name        varchar(255)                           not null,
    description varchar(255)                           not null,
    created     timestamp with time zone default now() not null,
    created_by  bigint                                 not null
);

create table if not exists news
(
    id          bigint                                 not null
        primary key,
    title       text                                   not null,
    content     text                                   not null,
    created     timestamp with time zone default now() not null,
    created_by  bigint                                 not null,
    thumbnail   jsonb,
    category_id bigint                                 not null
);

