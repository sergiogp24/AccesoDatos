create database RA3_Usuario;
use RA3_Usuario;

CREATE TABLE usuario(
                        id int primary key auto_increment not null,
                        nombre varchar(255),
                        nickname varchar(255) not null,
                        email varchar(255) unique not null,
                        password varchar(255) not null,
                        activo TINYINT (1) DEFAULT 1
);
select * from usuario;