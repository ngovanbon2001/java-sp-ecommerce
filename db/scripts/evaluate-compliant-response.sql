create table if not exists complaint_response_reviews
(
    id           bigserial             not null primary key,
    complaint_id    bigint                                                                        not null,
    user_id      bigint                                                                        not null,
    submitted_at timestamp with time zone default now()                                        not null,
    rating       integer                                                                       not null,
    comment      text
);