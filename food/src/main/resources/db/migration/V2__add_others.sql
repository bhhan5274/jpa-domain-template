/* Billing */
create table billings
(
    billing_id bigint not null auto_increment,
    commission bigint not null,
    shop_id    bigint not null,
    primary key (billing_id),
    index      ix_shop_id (shop_id)
) engine=InnoDB;

/* Delivery */
create table deliveries
(
    delivery_id     bigint       not null auto_increment,
    delivery_status varchar(255) not null,
    order_id        bigint       not null,
    primary key (delivery_id),
    index           ix_order_id (order_id)
) engine=InnoDB;

/* Order */
create table orders
(
    order_id     bigint       not null auto_increment,
    order_status varchar(255) not null,
    ordered_time datetime(6),
    shop_id      bigint       not null,
    user_id      bigint       not null,
    primary key (order_id),
    index        ix_shop_id (shop_id),
    index        ix_user_id (user_id)
) engine=InnoDB;

create table order_line_items
(
    order_line_item_id bigint       not null auto_increment,
    count              integer      not null,
    menu_id            bigint       not null,
    name               varchar(255) not null,
    order_id           bigint,
    primary key (order_line_item_id)
) engine=InnoDB;

alter table order_line_items
add constraint fk_order_id foreign key (order_id) references orders (order_id);

create table order_option_groups
(
    order_option_group_id bigint       not null auto_increment,
    name                  varchar(255) not null,
    order_line_item_id    bigint,
    primary key (order_option_group_id)
) engine=InnoDB;

alter table order_option_groups
add constraint fk_order_line_item_id foreign key (order_line_item_id) references order_line_items (order_line_item_id);

create table order_options
(
    order_option_id       bigint       not null auto_increment,
    name                  varchar(255) not null,
    price                 bigint       not null,
    order_option_group_id bigint,
    primary key (order_option_id)
) engine=InnoDB;

alter table order_options
add constraint fk_order_option_group_id foreign key (order_option_group_id) references order_option_groups (order_option_group_id);
