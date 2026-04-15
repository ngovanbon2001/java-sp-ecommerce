create table if not exists warning_categories
(
    id          bigserial primary key,
    name        varchar(255)                           not null,
    description varchar(255)                           not null,
    created     timestamp with time zone default now() not null,
    created_by  bigint                                 not null
);

create table if not exists warnings
(
    id          bigserial primary key,
    title       text                                   not null,
    content     text                                   not null,
    created     timestamp with time zone default now() not null,
    created_by  bigint                                 not null,
    thumbnail   jsonb,
    category_id bigint                                 not null
);

CREATE TABLE warning_reads
(
    user_id BIGINT      not null,
    post_id BIGINT      not null,
    read_at timestamptz not null default now(),
    PRIMARY KEY (user_id, post_id)
);

