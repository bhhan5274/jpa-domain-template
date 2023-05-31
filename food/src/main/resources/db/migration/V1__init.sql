/* Menu */
create table menus
(
    menu_id     bigint       not null auto_increment,
    description text         not null,
    name        varchar(255) not null,
    shop_id     bigint       not null,
    primary key (menu_id),
    unique key ux_name (name),
    index         ix_shop_id (shop_id)
) engine=InnoDB;

create table option_group_specs
(
    option_group_spec_id bigint       not null auto_increment,
    basic                bit          not null,
    exclusive            bit          not null,
    name                 varchar(255) not null,
    menu_id              bigint       not null,
    primary key (option_group_spec_id),
    unique key ux_name (name)
) engine=InnoDB;

alter table option_group_specs
add constraint fk_menu_id foreign key (menu_id) references menus(menu_id);

create table option_specs
(
    option_spec_id       bigint       not null auto_increment,
    name                 varchar(255) not null,
    price                bigint       not null,
    option_group_spec_id bigint       not null,
    primary key (option_spec_id),
    unique key ux_name (name)
) engine=InnoDB;

alter table option_specs
add constraint fk_option_group_spec_id foreign key (option_group_spec_id) references option_group_specs(option_group_spec_id);


/* Shop */
create table shops
(
    shop_id          bigint       not null auto_increment,
    commission_rate  double       not null,
    min_order_amount bigint       not null,
    name             varchar(255) not null,
    open             bit          not null,
    primary key (shop_id),
    unique key ux_name (name)
) engine=InnoDB;
