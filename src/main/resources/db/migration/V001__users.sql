create table users (
    id           bigserial primary key,
    email        text not null unique,
    name         text not null,
    role         text not null check (role in ('USER','SELLER','ADMIN')),
    created_at   timestamp not null default now(),
    updated_at   timestamp not null default now()
);
