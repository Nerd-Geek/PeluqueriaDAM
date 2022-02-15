drop database if exists `peluqueriaDAM`;
create database `peluqueriaDAM`;
use `peluqueriaDAM`;

create table appointment (id varchar(255) primary key, appointmentDate date, appointmentTime time, id_user varchar(255)) engine=InnoDB;
create table login (id varchar(255) primary key, instance datetime(6), token varchar(255), id_user varchar(255) not null) engine=InnoDB;
create table services (id varchar(255) primary key, description varchar(255), image varchar(255), name varchar(255), price double precision, stock integer) engine=InnoDB;
create table users (id varchar(255) primary key, email varchar(255), gender integer, image varchar(255), name varchar(255), password varchar(255), phone_number varchar(255), super_user bit not null, surname varchar(255), username varchar(255)) engine=InnoDB;
alter table services drop index if exists UK_h4rqgjwnqidx6mvj4i22dxwxe;
alter table services add constraint UK_h4rqgjwnqidx6mvj4i22dxwxe unique (name);
alter table appointment add constraint FK9l9i8coynlm7vocln34x1ingn foreign key (id_user) references users (id);
alter table login add constraint FKh5dnpi129vsjqrc1b9qc2xwck foreign key (id_user) references users (id);

# insert into users (id, email, gender, image, name, password, phone_number, super_user, surname, username) values ("c1334d57-120b-437b-baef-cf5b5f68cc3e", "porofernandez@freljorld.com", "Male", null, "poro", "braumILY", "234567890", true, "freljorld", "peludito150");
# insert into appointment (id, appointmentDate, appointmentTime, id_user) values ("5b4dce42-9142-469e-93c0-70ff2a26f03c", 2022-02-13, 19:07:01, "c1334d57-120b-437b-baef-cf5b5f68cc3e");
# insert into services (id, description, image, name, price, stock) values ("7dafe5fd-976b-450a-9bab-17ab450a4fff", "Este es un corte de pelo para gnomos", null, "Corte pelo", 100, 4);
# insert into login (id, image, instance, token, id_user) values ("233149e4-b6f3-4692-ac71-2e8123bc24b2", null, 2022-02-13 19:07:01.07, "123213412", "c1334d57-120b-437b-baef-cf5b5f68cc3e");
# TODO: ¿eliminar?