create table if not exists officer_reports
(
    id           bigserial
    primary key,
    content      text                                   not null,
    created      timestamp with time zone default now() not null,
    updated      timestamp with time zone default now() not null,
    created_by   bigint                                 not null,
    updated_by   bigint                                 not null,
    complaint_id bigint                                 not null
    );

