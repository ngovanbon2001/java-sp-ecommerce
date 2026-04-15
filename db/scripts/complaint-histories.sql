create table if not exists complaint_histories
(
    id           bigserial
        primary key,
    complaint_id bigint                                 not null,
    description  varchar(255)                           not null,
    user_id      bigint                                 not null,
    created      timestamp with time zone default now() not null
);

alter table officer_reports add column media_urls jsonb;

alter table officer_reports add column department_name varchar(255) not null default '';