
CREATE TABLE users
(
    id bigserial primary key,
    username varchar(64) unique not null,
    password varchar(64) not null,
    nicname varchar(64) unique not null,
    first_Name varchar(64),
    last_Name varchar(64),
    telephone varchar(64),
    registration_Date timestamp not null,
    status boolean not null,
    visibility boolean not null,
    role varchar(64) not null
);
