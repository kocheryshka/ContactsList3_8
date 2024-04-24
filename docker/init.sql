create schema if not exists contacts;

create table if not exists contacts.contact
(
    id bigint primary key,
    first_name varchar(256) not null,
    last_name varchar(256) not null,
    email varchar(256),
    phone varchar(256)
)