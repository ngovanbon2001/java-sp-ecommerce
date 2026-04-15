drop index departments__name_unique;

create index departments__name
    on departments (name);