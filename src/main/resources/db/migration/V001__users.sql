create table users
(
    id            bigserial primary key,
    email         text      not null unique,
    password_hash text      not null,
    name          text      not null,
    role          text      not null check (role in ('USER', 'ADMIN')) default 'USER',
    created_at    timestamp not null                                   default now(),
    updated_at    timestamp not null                                   default now(),
    is_active     boolean   not null                                   default true
);
