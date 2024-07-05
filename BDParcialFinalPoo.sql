CREATE DATABASE parcialFinal;
USE parcialFinal;

-- Tabla Clientes
CREATE TABLE Clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombreCompleto VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

-- Tabla Facilitadores
CREATE TABLE Facilitadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- Tabla Tarjetas
CREATE TABLE Tarjetas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numeroTarjeta VARCHAR(20) NOT NULL UNIQUE,
    fechaExpiracion DATE NOT NULL,
    tipo ENUM('Credito', 'Debito') NOT NULL,
    facilitadorId INT,
    clienteId INT,
    FOREIGN KEY (facilitadorId) REFERENCES Facilitadores(id),
    FOREIGN KEY (clienteId) REFERENCES Clientes(id)
);

-- Tabla Transacciones
CREATE TABLE Transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    montoTotal DECIMAL(10, 2) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    tarjetaId INT,
    FOREIGN KEY (tarjetaId) REFERENCES Tarjetas(id)
);

-- Inserción de datos en la tabla Facilitadores
INSERT INTO Facilitadores (nombre) VALUES ('Visa');
INSERT INTO Facilitadores (nombre) VALUES ('MasterCard');
INSERT INTO Facilitadores (nombre) VALUES ('American Express');

-- Inserción de datos en la tabla Clientes
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Juan Pérez', 'San Salvador', '2101-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('María López', 'Santa Ana', '2102-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Carlos Hernández', 'San Miguel', '2103-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Ana Martínez', 'Mejicanos', '2104-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Luis González', 'Soyapango', '2105-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Sofía Rodríguez', 'Apopa', '2106-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Ricardo Rivera', 'San Vicente', '2107-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Laura Díaz', 'Ahuachapán', '2108-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Pedro Ramos', 'Cojutepeque', '2109-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Elena Morales', 'Usulután', '2110-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Jorge Castillo', 'Chalatenango', '2111-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Natalia Guzmán', 'San Francisco Gotera', '2112-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Manuel Romero', 'Sonsonate', '2113-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Paula Flores', 'La Libertad', '2114-1234');
INSERT INTO Clientes (nombreCompleto, direccion, telefono) VALUES ('Francisco Cruz', 'Zacatecoluca', '2115-1234');

-- Inserción de datos en la tabla Tarjetas
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('1234567890123456', '2025-05-01', 'Credito', 1, 1);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('2345678901234567', '2026-06-01', 'Debito', 2, 2);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('3456789012345678', '2027-07-01', 'Credito', 3, 3);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('4567890123456789', '2028-08-01', 'Debito', 1, 4);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('5678901234567890', '2029-09-01', 'Credito', 2, 5);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('6789012345678901', '2030-10-01', 'Debito', 3, 6);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('7890123456789012', '2031-11-01', 'Credito', 1, 7);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('8901234567890123', '2032-12-01', 'Debito', 2, 8);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('9012345678901234', '2033-01-01', 'Credito', 3, 9);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('0123456789012345', '2034-02-01', 'Debito', 1, 10);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('1111222233334444', '2035-03-01', 'Credito', 2, 11);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('2222333344445555', '2036-04-01', 'Debito', 3, 12);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('3333444455556666', '2037-05-01', 'Credito', 1, 13);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('4444555566667777', '2038-06-01', 'Debito', 2, 14);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('5555666677778888', '2039-07-01', 'Credito', 3, 15);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('6666777788889999', '2040-08-01', 'Debito', 1, 1);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('7777888899990000', '2041-09-01', 'Credito', 2, 2);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('8888999900001111', '2042-10-01', 'Debito', 3, 3);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('9999000011112222', '2043-11-01', 'Credito', 1, 4);
INSERT INTO Tarjetas (numeroTarjeta, fechaExpiracion, tipo, facilitadorId, clienteId) VALUES ('0000111122223333', '2044-12-01', 'Debito', 2, 5);

-- Inserción de datos en la tabla Transacciones
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-01 10:00:00', 100.50, 'Compra en Supermercado La Colonia', 1);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-02 11:00:00', 200.75, 'Compra en Tienda de Ropa Mango', 2);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-03 12:00:00', 300.20, 'Compra en Tienda de Electronica Casa Rivas', 3);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-04 13:00:00', 400.10, 'Compra en Farmacia San Nicolás', 4);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-05 14:00:00', 500.50, 'Compra en Librería San Salvador', 5);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-06 15:00:00', 600.30, 'Compra en Tienda de Deportes Adidas', 6);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-07 16:00:00', 700.40, 'Compra en Restaurante de Burguer King', 7);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-08 17:00:00', 800.25, 'Compra en Almacenes Siman', 8);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-09 18:00:00', 900.15, 'Compra en Tienda de Zapatos Payless', 9);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-10 19:00:00', 1000.50, 'Compra en Supermercado Selectos', 10);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-11 20:00:00', 1100.75, 'Compra en Tienda de Electrónicos RadioShack', 11);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-12 21:00:00', 1200.20, 'Compra en Farmacia Económica', 12);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-13 22:00:00', 1300.10, 'Compra en Librería La Ceiba', 13);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-14 23:00:00', 1400.50, 'Compra en Tienda de Ropa Zara', 14);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-15 10:00:00', 1500.30, 'Compra en Tienda de Deportes Nike', 15);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-16 11:00:00', 1600.40, 'Compra en Tienda de Juguetes Juguetón', 1);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-17 12:00:00', 1700.25, 'Compra en Almacenes Sears', 2);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-18 13:00:00', 1800.15, 'Compra en Tienda de Zapatos Skechers', 3);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-19 14:00:00', 1900.50, 'Compra en Supermercado La Torre', 4);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-20 15:00:00', 2000.75, 'Compra en Tienda de Electrónicos Best Buy', 5);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-21 16:00:00', 2100.20, 'Compra en Farmacia La Buena', 6);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-22 17:00:00', 2200.10, 'Compra en Librería Casa del Libro', 7);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-23 18:00:00', 2300.50, 'Compra en Tienda de Ropa H&M', 8);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-24 19:00:00', 2400.30, 'Compra en Tienda de Deportes Under Armour', 9);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-25 20:00:00', 2500.40, 'Compra en Tienda de Juguetes Hasbro', 10);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-26 21:00:00', 2600.25, 'Compra en Almacenes Walmart', 11);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-27 22:00:00', 2700.15, 'Compra en Tienda de Zapatos Clarks', 12);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-28 23:00:00', 2800.50, 'Compra en Supermercado PriceSmart', 13);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-29 10:00:00', 2900.75, 'Compra en Tienda de Electrónicos Newegg', 14);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-30 11:00:00', 3000.20, 'Compra en Farmacia Walmart', 15);

-- Consulta de Facilitadores
SELECT f.id, f.nombre
FROM Facilitadores f;

-- Consulta de Clientes
SELECT c.nombreCompleto AS Cliente, c.direccion AS DireccionCliente, c.telefono AS TelefonoCliente
FROM Clientes c;

-- Consulta de Tarjetas
SELECT t.numeroTarjeta AS NumeroTarjeta, t.fechaExpiracion AS FechaExpiracion, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, c.nombreCompleto AS Cliente
FROM Tarjetas t
INNER JOIN Facilitadores f ON t.facilitadorId = f.id
INNER JOIN Clientes c ON t.clienteId = c.id;

-- Consulta de Transacciones
SELECT tr.fecha AS FechaTransaccion, tr.montoTotal AS MontoTotal, tr.descripcion AS Descripcion, t.numeroTarjeta AS NumeroTarjeta, f.nombre AS Facilitador, c.nombreCompleto AS Cliente
FROM Transacciones tr
INNER JOIN Tarjetas t ON tr.tarjetaId = t.id
INNER JOIN Facilitadores f ON t.facilitadorId = f.id
INNER JOIN Clientes c ON t.clienteId = c.id;



