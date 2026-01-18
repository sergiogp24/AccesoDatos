drop database clase;
create database if not exists clase;
use clase;
create table usuario(
    id int primary key auto_increment not null,
    username varchar(100) unique not null,
    password varchar(255) not null,
    activo tinyint(1) default 1
)
select * from usuario;