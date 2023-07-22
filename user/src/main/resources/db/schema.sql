drop table if exists "user";

create table "user"
(
    id          bigserial
        primary key,
    email       varchar(255) not null
        unique,
    username    varchar(255),
    account     varchar(255) not null,
    password    varchar(255) not null,
    avatar      varchar(255),
    create_time date,
    update_time date,
    deleted     boolean
);

comment on column "user".id is 'userId';

comment on column "user".email is 'email';

comment on column "user".username is 'username';

comment on column "user".account is 'account';

comment on column "user".password is 'password';

comment on column "user".avatar is 'avatar';

comment on column "user".create_time is 'create_time';

comment on column "user".update_time is 'update_time';

comment on column "user".deleted is 'deleted';

create unique index unique_email_idx
    on "user" (email);

create index login_search_idx_email
    on "user" (email, password);

create index login_search_idx_account
    on "user" (account, password);

