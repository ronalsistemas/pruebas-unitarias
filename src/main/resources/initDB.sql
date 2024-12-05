-- Crear la tabla propietario
CREATE TABLE propietario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    ciudad VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL
);

-- Crear la tabla mascota
CREATE TABLE mascota (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    propietario_id INTEGER NOT NULL,
    FOREIGN KEY (propietario_id) REFERENCES propietario(id)
);