create table refresh_tokens
(
    id          bigserial primary key,
    user_id     bigint       not null references users (id) on delete cascade,
    token       varchar(512) not null unique,
    issued_at   timestamp    not null default now(),
    expires_at  timestamp    not null,
    fingerprint text         not null,
    revoked     boolean      not null default false
);