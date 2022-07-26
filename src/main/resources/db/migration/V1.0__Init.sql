create table if not exists account(
    id serial  not null unique ,
    account_name  varchar(100)not null,
    account_password varchar(100) not null,
    count_login integer ,
    account_block boolean,
    created_at timestamp,
    primary key(id)

);


create table if not exists account_role(
    account_id integer  references account(id) on delete cascade,
    roles varchar(10)
);

create table if not exists pet(
    id serial not null,
    day_bith timestamp not null,
    pet_sex varchar(10) not null,
    pet_name varchar,
    primary key(id)

);