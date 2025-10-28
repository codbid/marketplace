create table products
(
    id          bigserial primary key,
    seller_id   bigint       not null,
    name        varchar(200) not null,
    slug        text         not null unique,
    description text,
    price_cents int          not null check (price_cents >= 0),
    stock       int          not null check (stock >= 0),
    is_active   boolean      not null default false,
    created_by  bigint       not null,
    created_at  timestamp    not null default now(),
    updated_at  timestamp    not null default now(),

    constraint products_seller_id_fkey
        foreign key (seller_id) references sellers (id) on delete cascade,
    constraint products_created_by_fkey
        foreign key (created_by) references users (id) on delete restrict
);

create table orders
(
    id          bigserial primary key,
    buyer_id    bigint    not null,
    created_at  timestamp not null default now(),

    constraint orders_buyer_id_fkey
        foreign key (buyer_id) references users (id) on delete restrict
);

create table order_items
(
    id          bigserial primary key,
    order_id    bigint    not null,
    product_id  bigint    not null,
    status      text      not null check (status in ('NEW', 'RESERVED', 'PAID', 'CANCELLED', 'REFUNDED')),
    total_cents int       not null check (total_cents >= 0),
    quantity    int       not null check (quantity > 0),
    created_at  timestamp not null default now(),
    updated_at  timestamp not null default now(),

    constraint order_items_order_id
        foreign key (order_id) references orders (id) on delete restrict,
    constraint order_items_product_id
        foreign key (product_id) references products (id) on delete restrict
);