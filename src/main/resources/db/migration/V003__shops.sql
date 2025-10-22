alter table users add column verified boolean not null default false;
alter table users add column verified_at timestamp;

create table sellers
(
    id         bigserial primary key,
    owner_id   bigint    not null,
    name       text      not null,
    slug       text      not null unique,
    status     text      not null default 'DRAFT' check (status in ('DRAFT', 'ACTIVE', 'SUSPENDED')),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now(),
    deleted_at timestamp,

    constraint sellers_owner_id_fkey
        foreign key (owner_id) references users (id) on delete restrict
);

create table seller_staff
(
    id          bigserial primary key,
    seller_id   bigint    not null,
    user_id     bigint    not null,
    role        text      not null default 'OPERATOR' check ( role in ('OPERATOR') ),
    employed_at timestamp not null default now(),

    constraint seller_staff_seller_id_fkey
        foreign key (seller_id) references sellers (id) on delete cascade,
    constraint seller_staff_user_id_fkey
        foreign key (user_id) references users (id) on delete cascade
);