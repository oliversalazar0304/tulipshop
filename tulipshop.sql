DROP DATABASE IF EXISTS tulipshop;
CREATE DATABASE tulipshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tulipshop;

CREATE TABLE roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre_completo VARCHAR(120) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    direccion VARCHAR(250) NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES roles(id)
);

CREATE TABLE categorias (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(250),
    PRIMARY KEY (id)
);

CREATE TABLE productos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    imagen_url VARCHAR(250),
    categoria_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE metodos_pago (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tipo_tarjeta VARCHAR(50) NOT NULL,
    numero_enmascarado VARCHAR(20) NOT NULL,
    nombre_titular VARCHAR(100) NOT NULL,
    fecha_expiracion VARCHAR(20) NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_metodo_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE carritos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_carrito_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE carrito_detalle (
    id BIGINT NOT NULL AUTO_INCREMENT,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_detalle_carrito FOREIGN KEY (carrito_id) REFERENCES carritos(id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
);

CREATE TABLE pedidos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    metodo_pago_id BIGINT NOT NULL,
    fecha_pedido DATETIME NOT NULL,
    fecha_entrega DATE NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    impuesto DECIMAL(10,2) NOT NULL,
    envio DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_pedido_metodo FOREIGN KEY (metodo_pago_id) REFERENCES metodos_pago(id)
);

CREATE TABLE pedido_detalle (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_pedidodetalle_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    CONSTRAINT fk_pedidodetalle_producto FOREIGN KEY (producto_id) REFERENCES productos(id)
);

INSERT INTO roles (nombre) VALUES ('ADMIN');
INSERT INTO roles (nombre) VALUES ('CLIENTE');

INSERT INTO usuarios (nombre_completo, correo, password, direccion, rol_id)
VALUES ('Administrador Tulip Shop', 'admin@tulipshop.com', '123', 'Heredia, Costa Rica', 1);

INSERT INTO usuarios (nombre_completo, correo, password, direccion, rol_id)
VALUES ('Cliente Demo', 'cliente@tulipshop.com', '123', 'San José, Costa Rica', 2);

INSERT INTO categorias (nombre, descripcion) VALUES
('Cumpleaños', 'Arreglos florales para cumpleaños'),
('Citas', 'Arreglos románticos'),
('Condolencias', 'Arreglos sobrios y elegantes'),
('Aniversarios', 'Diseños especiales para celebrar amor y unión'),
('Graduaciones', 'Arreglos para felicitar logros académicos');

INSERT INTO productos (nombre, descripcion, precio, stock, imagen_url, categoria_id) VALUES
('Ramo Rosado', 'Hermoso arreglo floral en tonos rosados', 15000.00, 10, 'ramorosado.jpg', 1),
('Bouquet Romance', 'Bouquet especial para una cita romántica', 18500.00, 8, 'bouquetromance.jpg', 2),
('Arreglo Elegante', 'Arreglo floral sobrio para condolencias', 22000.00, 5, 'arregloelegante.jpg', 3),
('Caja de Tulipanes', 'Caja decorativa con tulipanes variados', 19500.00, 6, 'cajatulipanes.jpg', 4),
('Bouquet Celebración', 'Ramo alegre ideal para graduaciones', 17000.00, 12, 'bouquetcelebracion.jpg', 5);

INSERT INTO metodos_pago (tipo_tarjeta, numero_enmascarado, nombre_titular, fecha_expiracion, usuario_id)
VALUES ('VISA', '**** **** **** 1234', 'Cliente', '2029-12', 2);

INSERT INTO carritos (usuario_id, fecha_creacion, estado)
VALUES (2, NOW(), 'ACTIVO');
