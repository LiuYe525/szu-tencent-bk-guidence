drop table if exists users;
create table users
(
    id int auto_increment,
    name varchar(64),
    age int,
    email varchar(64),
    primary key (id)
);