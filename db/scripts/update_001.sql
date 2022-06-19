create table candidates(
    id serial primary key,
    name varchar,
    experience INTEGER,
    salary INTEGER
);

create table j_role (
    id serial primary key,
    name varchar(2000)
);

create table j_user (
    id serial primary key,
    name varchar(2000),
    role_id int not null references j_role(id)
);

create table if not exists car_model(
    id serial primary key,
    name varchar
);

create table if not exists car_brand(
    id serial primary key,
    name varchar
);