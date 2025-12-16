-- Script SQL de ejemplo para crear las tablas necesarias para la librería direccions-lib
-- Estas tablas deben existir en tu base de datos para que la librería funcione correctamente

-- Tabla de países
CREATE TABLE md_pais (
    id_pais BIGSERIAL PRIMARY KEY,
    codi VARCHAR(10),
    nom VARCHAR(128)
);

-- Tabla de comunidades autónomas
CREATE TABLE md_comunitat_autonoma (
    id_comunitat_autonoma BIGSERIAL PRIMARY KEY,
    codi VARCHAR(10),
    nom VARCHAR(255),
    id_pais BIGINT REFERENCES md_pais(id_pais)
);

-- Tabla de provincias
CREATE TABLE md_provincia (
    id_provincia BIGSERIAL PRIMARY KEY,
    codi VARCHAR(10),
    nom VARCHAR(255),
    id_comunitat_autonoma BIGINT REFERENCES md_comunitat_autonoma(id_comunitat_autonoma)
);

-- Tabla de municipios
CREATE TABLE md_municipi (
    id_municipi BIGSERIAL PRIMARY KEY,
    codi VARCHAR(10),
    nom VARCHAR(255),
    comarca VARCHAR(50),
    id_provincia BIGINT REFERENCES md_provincia(id_provincia)
);

-- Tabla de códigos postales
CREATE TABLE md_codi_postal (
    id_codi_postal BIGSERIAL PRIMARY KEY,
    codi_postal VARCHAR(5),
    id_municipi BIGINT REFERENCES md_municipi(id_municipi)
);

-- Tabla de tipos de vía
CREATE TABLE md_tipus_via (
    id_tipus_via BIGSERIAL PRIMARY KEY,
    codi VARCHAR(10),
    nom VARCHAR(64)
);

-- Tabla de nombres de calle registrados
CREATE TABLE md_street_name (
    id_street_name BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    id_municipi BIGINT NOT NULL REFERENCES md_municipi(id_municipi)
);

-- Tabla de direcciones
CREATE TABLE md_direccio (
    id_direccio BIGSERIAL PRIMARY KEY,
    id_pais BIGINT REFERENCES md_pais(id_pais),
    id_comunitat_autonoma BIGINT REFERENCES md_comunitat_autonoma(id_comunitat_autonoma),
    id_provincia BIGINT REFERENCES md_provincia(id_provincia),
    id_municipi BIGINT REFERENCES md_municipi(id_municipi),
    id_codi_postal BIGINT REFERENCES md_codi_postal(id_codi_postal),
    id_tipus_via BIGINT REFERENCES md_tipus_via(id_tipus_via),
    nom_via VARCHAR(128),
    numero VARCHAR(128),
    pis VARCHAR(128),
    municipi_no_catala VARCHAR(255),
    codi_postal_no_catala VARCHAR(10)
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_comunitat_autonoma_pais ON md_comunitat_autonoma(id_pais);
CREATE INDEX idx_provincia_comunitat ON md_provincia(id_comunitat_autonoma);
CREATE INDEX idx_municipi_provincia ON md_municipi(id_provincia);
CREATE INDEX idx_codi_postal_municipi ON md_codi_postal(id_municipi);
CREATE INDEX idx_street_name_municipi ON md_street_name(id_municipi);
CREATE INDEX idx_street_name_nom ON md_street_name(nom);

-- Datos de ejemplo (España)
INSERT INTO md_pais (id_pais, codi, nom) VALUES (1, 'ESP', 'España');

-- Ejemplos de comunidades autónomas
INSERT INTO md_comunitat_autonoma (id_comunitat_autonoma, codi, nom, id_pais) VALUES
    (1, 'CAT', 'Cataluña', 1),
    (2, 'MAD', 'Madrid', 1),
    (3, 'AND', 'Andalucía', 1);

-- Ejemplos de provincias
INSERT INTO md_provincia (id_provincia, codi, nom, id_comunitat_autonoma) VALUES
    (1, 'B', 'Barcelona', 1),
    (2, 'GI', 'Girona', 1),
    (3, 'M', 'Madrid', 2);

-- Ejemplos de tipos de vía
INSERT INTO md_tipus_via (id_tipus_via, codi, nom) VALUES
    (1, 'C/', 'Calle'),
    (2, 'AV', 'Avenida'),
    (3, 'PL', 'Plaza'),
    (4, 'PG', 'Passeig');

-- Ejemplos de municipios
INSERT INTO md_municipi (id_municipi, codi, nom, comarca, id_provincia) VALUES
    (1, '08019', 'Barcelona', 'Barcelonès', 1),
    (2, '08101', 'Girona', 'Gironès', 2),
    (3, '28079', 'Madrid', 'Madrid', 3);

-- Ejemplos de códigos postales
INSERT INTO md_codi_postal (id_codi_postal, codi_postal, id_municipi) VALUES
    (1, '08001', 1),
    (2, '08002', 1),
    (3, '17001', 2),
    (4, '28001', 3);

-- Ejemplos de nombres de calles registradas
INSERT INTO md_street_name (id_street_name, nom, id_municipi) VALUES
    (1, 'Gran Via de les Corts Catalanes', 1),
    (2, 'Passeig de Gràcia', 1),
    (3, 'Carrer de Balmes', 1),
    (4, 'Gran Via', 3),
    (5, 'Calle Mayor', 3);
