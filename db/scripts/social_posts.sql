create table social_posts
(
    id            bigint not null primary key,
    category_id   bigint not null,
    content       text   not null,
    media_urls    jsonb,
    created_by    bigint not null,
    comment_count int    not null,
    like_count    int    not null
);

alter table social_posts
    add column created timestamptz not null default now();
alter table social_posts
    add column updated timestamptz not null default now();
alter table social_posts
    add column updated_by bigint not null;

create table social_post_categories
(
    id          bigserial    not null primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    created     timestamptz  not null default now(),
    created_by  bigint       not null
);

create index if not exists content_ts_idx
    on social_posts using gin (to_tsvector('simple'::regconfig, immutable_unaccent(COALESCE(content, ''::text))));