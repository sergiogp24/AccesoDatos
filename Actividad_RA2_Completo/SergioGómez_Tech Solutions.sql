
CREATE DATABASE libreria;
USE libreria;
-- Tabla Genero 
CREATE TABLE genero(
id_genero INT AUTO_INCREMENT PRIMARY KEY,
tipo VARCHAR(255));

-- Tabla Libro 
CREATE TABLE libro (
id_libro INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
stock INT,
id_genero INT,
FOREIGN KEY (id_genero) REFERENCES genero(id_genero)
ON DELETE CASCADE ON UPDATE CASCADE);

-- Tabla Cliente
CREATE TABLE cliente(
id_cliente INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(255) not null);

-- Tabla Pedido
CREATE TABLE pedido(
id_pedido INT AUTO_INCREMENT PRIMARY KEY,
fecha DATE,
id_libro INT,
FOREIGN KEY (id_libro) REFERENCES libro(id_libro)
ON DELETE CASCADE ON UPDATE CASCADE,
id_cliente INT,
FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
ON DELETE CASCADE ON UPDATE CASCADE);

-- Tabla Venta
CREATE TABLE venta(
id_venta INT AUTO_INCREMENT PRIMARY KEY,
precio DECIMAL(10,2),
id_pedido INT,
FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
ON DELETE CASCADE ON UPDATE CASCADE);

-- Datos de prueba
INSERT INTO genero (tipo) VALUES
('Poesia'),
('Novela'),
('Historia'),
('Cuento infantil');

INSERT INTO libro (nombre, stock, id_genero) VALUES
('Los campos de amor',20, 1),
('Los pitufos',10, 4),
('La Conquista',15,  3),
('Rapuncel',14, 2);

INSERT INTO cliente (nombre ) VALUES
('Lucas '),
('Antonio'),
('Maria' ),
('Sergio' );
INSERT INTO pedido (fecha, id_libro, id_cliente) VALUES
('2023-01-10', 2,1),
('2024-01-10',1,3),
('2023-05-10', 3, 2),
('2025-01-10', 4, 4);
INSERT INTO venta (precio, id_pedido) VALUES
(13.50, 2),
(20.00,1),
(15.50, 3),
(10.20, 4);

-- Procedimiento
--  Actualizar salario por departamento
DELIMITER $$
CREATE PROCEDURE actualizar_stock(
    IN p_nombre VARCHAR(50),
    IN p_stock int,
    OUT p_stock_actualizado INT
)
BEGIN
    UPDATE libro
    SET stock = (stock  + p_stock)
    WHERE nombre = p_nombre ;

    SET p_stock_actualizado = ROW_COUNT();
END$$
DELIMITER ;
select * from libro;



