create table departments
(
    id          bigserial
        constraint departments_pk
            primary key,
    owner_id bigint,
    name        varchar(255) not null,
    description text,
    path text,
    created_at  timestamp with time zone not null default now(),
    updated_at  timestamp with time zone not null default now(),
    deleted_at  timestamp with time zone
);

create index departments_owner_id
    on departments (owner_id);

create unique index departments__name_unique
    on departments (name);

create table staff_departments
(
    id            bigserial not null
        constraint staff_departments_pk
            primary key,
    user_id       bigint    not null,
    department_id bigint    not null,
    role          varchar(50),
    created_at    timestamp with time zone not null default now(),
    updated_at    timestamp with time zone not null default now(),
    deleted_at    timestamp with time zone
);

create index staff_departments__department_id_index
    on staff_departments (department_id);

create index staff_departments_user_department_index
    on staff_departments (user_id, department_id);

create index staff_departments_user_id_index
    on staff_departments (user_id);


create table ward_administrators
(
    id               bigserial not null
        constraint ward_administrators_pk
            primary key,
    ward_code        varchar(20),
    administrator_id bigint    not null,
    created_at       timestamp with time zone not null default now(),
    updated_at       timestamp with time zone not null default now(),
    deleted_at       timestamp with time zone
);

create index ward_administrators__administrator_index
    on ward_administrators (administrator_id);

create index ward_administrators__wardcode_administrator_id_index
    on ward_administrators (ward_code, administrator_id);

create index ward_administrators__wardcode_index
    on ward_administrators (ward_code);
