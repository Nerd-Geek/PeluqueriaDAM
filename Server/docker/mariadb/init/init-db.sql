drop database if exists `peluqueriaDAM`;
create database `peluqueriaDAM`;
use `peluqueriaDAM`;

create table appointment (id varchar(255) not null, date date, time time, user varchar(255), primary key (id)) engine=InnoDB;
create table login (id varchar(255) not null, image varchar(255), instance datetime(6), token varchar(255), user varchar(255) not null, primary key (id)) engine=InnoDB;
create table services (id varchar(255) not null, description varchar(255), image varchar(255), name varchar(255), price double precision, stock integer, primary key (id)) engine=InnoDB;
create table users (id varchar(255) not null, email varchar(255), gender integer, image varchar(255), name varchar(255), password varchar(255), phone_number varchar(255), super_user bit not null, surname varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
alter table services drop index if exists UK_h4rqgjwnqidx6mvj4i22dxwxe;
alter table services add constraint UK_h4rqgjwnqidx6mvj4i22dxwxe unique (name);
alter table appointment add constraint FK9l9i8coynlm7vocln34x1ingn foreign key (user) references users (id);
alter table login add constraint FKh5dnpi129vsjqrc1b9qc2xwck foreign key (user) references users (id);
