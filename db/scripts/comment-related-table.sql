create table if not exists comments
(
    id        bigserial
    constraint comment_pkey
    primary key,
    post_id   bigint                                 not null,
    user_id   bigint                                 not null,
    content   text                                   not null,
    created   timestamp with time zone default now() not null,
    parent_id bigint,
    num_child integer                  default 0     not null
    );

create table if not exists comment_paths
(
    ancestor_id   bigint  not null,
    descendant_id bigint  not null,
    depth         integer not null,
    constraint comment_relations_pkey
    primary key (ancestor_id, descendant_id)
    );

create index if not exists comment_relations_ancestor_id_idx
    on comment_paths (descendant_id, ancestor_id);
create table if not exists files
(
    id        varchar(255)           not null primary key,
    url       varchar(255)                                                       not null,
    file_name text                                                               not null,
    file_size bigint                                                             not null,
    mime_type varchar(255),
    created   timestamp with time zone default now()                             not null,
    fid       varchar(255)                                                       not null,
    volume    varchar(255)                                                       not null,
    post_id   bigint
    );
