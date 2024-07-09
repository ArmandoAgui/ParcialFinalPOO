DROP DATABASE parcialFinal
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
                          tipo ENUM('Credito','Debito') NOT NULL,
                          facilitadorId INT,
                          clienteId INT,
                          FOREIGN KEY (facilitadorId) REFERENCES Facilitadores(id) ON DELETE CASCADE,
                          FOREIGN KEY (clienteId) REFERENCES Clientes(id) ON DELETE CASCADE
);

-- Tabla Transacciones
CREATE TABLE Transacciones (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               fecha DATE NOT NULL,
                               montoTotal DECIMAL(10, 2) NOT NULL,
                               descripcion VARCHAR(255) NOT NULL,
                               tarjetaId INT,
                               FOREIGN KEY (tarjetaId) REFERENCES Tarjetas(id) ON DELETE CASCADE
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
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-01', 100.50, 'Compra en Supermercado La Colonia', 1);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-02', 200.75, 'Compra en Tienda de Ropa Mango', 2);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-03', 300.20, 'Compra en Tienda de Electronica Casa Rivas', 3);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-04', 400.10, 'Compra en Farmacia San Nicolás', 4);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-05', 500.50, 'Compra en Librería San Salvador', 5);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-06', 600.30, 'Compra en Tienda de Deportes Adidas', 6);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-07', 700.40, 'Compra en Restaurante de Burguer King', 7);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-08', 800.25, 'Compra en Almacenes Siman', 8);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-09', 900.15, 'Compra en Tienda de Zapatos Payless', 9);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-10', 1000.50, 'Compra en Supermercado Selectos', 10);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-11', 1100.75, 'Compra en Tienda de Electrónicos RadioShack', 11);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-12', 1200.20, 'Compra en Farmacia Económica', 12);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-13', 1300.10, 'Compra en Librería La Ceiba', 13);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-14', 1400.50, 'Compra en Tienda de Ropa Zara', 14);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-15', 1500.30, 'Compra en Tienda de Deportes Nike', 15);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-16', 1600.40, 'Compra en Tienda de Juguetes Juguetón', 16);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-17', 1700.25, 'Compra en Almacenes Sears', 17);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-18', 1800.15, 'Compra en Tienda de Zapatos Skechers', 18);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-19', 1900.50, 'Compra en Supermercado La Torre', 19);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-20', 2000.75, 'Compra en Tienda de Electrónicos Best Buy', 20);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-21', 2100.20, 'Compra en Farmacia La Buena', 1);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-22', 2200.10, 'Compra en Librería Casa del Libro', 4);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-23', 2300.50, 'Compra en Tienda de Ropa H&M', 7);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-24', 2400.30, 'Compra en Tienda de Deportes Under Armour', 10);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-25', 2500.40, 'Compra en Tienda de Juguetes Hasbro', 13);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-26', 2600.25, 'Compra en Almacenes Walmart', 16);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-27', 2700.15, 'Compra en Tienda de Zapatos Clarks', 19);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-28', 2800.50, 'Compra en Supermercado PriceSmart', 1);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-29', 2900.75, 'Compra en Tienda de Electrónicos Newegg', 1);
INSERT INTO Transacciones (fecha, montoTotal, descripcion, tarjetaId) VALUES ('2024-01-30', 3000.20, 'Compra en Farmacia Walmart', 1);

-- Consulta de Facilitadores
SELECT f.id, f.nombre
FROM Facilitadores f;

-- Consulta de Clientes
SELECT c.nombreCompleto AS Cliente, c.direccion AS DireccionCliente, c.telefono AS TelefonoCliente
FROM Clientes c;

-- Consulta de Tarjetas
SELECT t.id, t.numeroTarjeta AS NumeroTarjeta, t.fechaExpiracion AS FechaExpiracion, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, c.nombreCompleto AS Cliente
FROM Tarjetas t
         INNER JOIN Facilitadores f ON t.facilitadorId = f.id
         INNER JOIN Clientes c ON t.clienteId = c.id
         ORDER BY t.id ASC

-- Consulta de Transacciones
SELECT tr.fecha AS FechaTransaccion, tr.montoTotal AS MontoTotal, tr.descripcion AS Descripcion, t.numeroTarjeta AS NumeroTarjeta, f.nombre AS Facilitador, c.nombreCompleto AS Cliente
FROM Transacciones tr
         INNER JOIN Tarjetas t ON tr.tarjetaId = t.id
         INNER JOIN Facilitadores f ON t.facilitadorId = f.id
         INNER JOIN Clientes c ON t.clienteId = c.id;

-- Reporte D
-- Consulta para obtener clientes que han realizado compras con un facilitador de tarjeta específico
SELECT c.id, c.nombreCompleto AS Cliente, COUNT(t.id) AS CantidadCompras, SUM(tr.montoTotal) AS TotalGastado, f.nombre AS Facilitador
FROM Clientes c
         INNER JOIN Tarjetas t ON c.id = t.clienteId
         INNER JOIN Facilitadores f ON t.facilitadorId = f.id
         INNER JOIN Transacciones tr ON t.id = tr.tarjetaId
WHERE t.facilitadorId = 1
GROUP BY c.id, c.nombreCompleto;


SELECT c.id, c.nombreCompleto, c.direccion, c.telefono
FROM Clientes c

SELECT
    c.id AS ClienteID,
    c.nombreCompleto AS NombreCliente,
    MONTHNAME(t.fecha) AS NombreMes,
    SUM(t.montoTotal) AS TotalGastado
FROM
    Clientes c
        INNER JOIN
    Tarjetas tj ON c.id = tj.clienteId
        INNER JOIN
    Transacciones t ON tj.id = t.tarjetaId
WHERE
    c.dui = '123456789'
        AND YEAR(t.fecha) = 2024
  AND MONTH(t.fecha) = 1
GROUP BY
    c.id, c.nombreCompleto, NombreMes;
    

SELECT t.id, t.numeroTarjeta AS NumeroTarjeta, t.fechaExpiracion AS FechaExpiracion, t.tipo AS TipoTarjeta, f.nombre AS Facilitador, t.clienteId 
                FROM TARJETAS t 
                INNER JOIN Facilitadores f ON t.facilitadorId = f.id
                ORDER BY t.id ASC
                
SELECT tr.id, tr.fecha, tr.montoTotal, tr.descripcion, tr.tarjetaId FROM Transacciones tr