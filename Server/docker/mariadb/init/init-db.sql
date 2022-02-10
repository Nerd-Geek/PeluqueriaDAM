drop database if exists `peluqueriaDAM`;
create database `peluqueriaDAM`;
use `peluqueriaDAM`;

create table users (id varchar(255) not null, email varchar(255), gender integer, name varchar(255), password varchar(255), phone_number varchar(255), super_user bit not null, surname varchar(255), username varchar(255), primary key (id)) engine=InnoDB;