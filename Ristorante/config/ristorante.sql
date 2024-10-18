DROP DATABASE IF EXISTS ristorante;
CREATE DATABASE ristorante;
USE ristorante;

CREATE TABLE IF NOT EXISTS clienti (
    id_cliente INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    data_nascita DATE,
    nome VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS ordini (
    id_ordine INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    stato VARCHAR(50),
    id_cliente INT NOT NULL,
    tavolo INT,
    FOREIGN KEY (id_cliente) REFERENCES clienti(id_cliente)
);

CREATE TABLE IF NOT EXISTS piatti (
    id_piatto INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    prezzo INT,
    descrizione VARCHAR(255),
    tempo_preparazione INT
);

CREATE TABLE IF NOT EXISTS ingredienti (
    id_ingrediente INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    scadenza DATE
);

CREATE TABLE IF NOT EXISTS intolleranze (
    tipo VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS ingredienti_intolleranze (
    id_ingrediente INT NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_ingrediente, tipo),
    FOREIGN KEY (id_ingrediente) REFERENCES ingredienti(id_ingrediente),
    FOREIGN KEY (tipo) REFERENCES intolleranze(tipo)
);

CREATE TABLE IF NOT EXISTS ordini_piatti (
    id_piatto INT NOT NULL,
    id_ordine INT NOT NULL,
    PRIMARY KEY (id_piatto, id_ordine),
    FOREIGN KEY (id_piatto) REFERENCES piatti(id_piatto),
    FOREIGN KEY (id_ordine) REFERENCES ordini(id_ordine)
);

-- Creating 'piatti_ingredienti' table
CREATE TABLE IF NOT EXISTS piatti_ingredienti (
    id_piatto INT NOT NULL,
    id_ingrediente INT NOT NULL,
    PRIMARY KEY (id_piatto, id_ingrediente),
    FOREIGN KEY (id_piatto) REFERENCES piatti(id_piatto),
    FOREIGN KEY (id_ingrediente) REFERENCES ingredienti(id_ingrediente)
);

-- Populating 'clienti' table
INSERT INTO clienti (data_nascita, nome) VALUES
('1980-05-15', 'Mario Rossi'),
('1992-10-20', 'Luca Bianchi'),
('1975-07-30', 'Giulia Verdi');

-- Populating 'ordini' table
INSERT INTO ordini (stato, id_cliente, tavolo) VALUES
('In attesa', 1, 5),
('Consegnato', 2, 3),
('In preparazione', 3, 7);

-- Populating 'piatti' table
INSERT INTO piatti (nome, prezzo, descrizione, tempo_preparazione) VALUES
('Spaghetti Carbonara', 12, 'Pasta con guanciale, uova, pecorino e pepe', 15),
('Pizza Margherita', 8, 'Pizza con pomodoro, mozzarella e basilico', 10),
('Tiramisù', 6, 'Dolce al cucchiaio con savoiardi, caffè e mascarpone', 5),
('Vino Rosso', 15, 'Vino rosso della casa', 0),
('Birra', 5, 'Birra artigianale', 0),
('Acqua Minerale', 2, 'Acqua minerale naturale', 0),
('Succo d\'Arancia', 3, 'Succo di arancia fresco', 0);

-- Populating 'ingredienti' table
INSERT INTO ingredienti (nome, scadenza) VALUES
('Pomodoro', '2024-12-31'),
('Mozzarella', '2024-11-15'),
('Guanciale', '2024-10-10'),
('Uova', '2024-08-01'),
('Pecorino', '2024-07-10'),
('Pepe', '2024-09-20'),
('Basilico', '2024-06-30'),
('Caffè', '2024-05-25'),
('Savoiardi', '2024-04-30'),
('Mascarpone', '2024-12-01'),
('Uva', '2024-09-15'),
('Luppolo', '2024-08-10'),
('Arancia', '2024-11-01');

-- Populating 'intolleranze' table
INSERT INTO intolleranze (tipo) VALUES
('Glutine'),
('Lattosio'),
('Noci');

-- Populating 'ingredienti_intolleranze' table
INSERT INTO ingredienti_intolleranze (id_ingrediente, tipo) VALUES
(1, 'Glutine'),
(2, 'Lattosio'),
(3, 'Glutine'),
(4, 'Lattosio'),
(5, 'Glutine');

-- Populating 'ordini_piatti' table
INSERT INTO ordini_piatti (id_piatto, id_ordine) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 1),
(5, 2),
(6, 3);

-- Populating 'piatti_ingredienti' table
INSERT INTO piatti_ingredienti (id_piatto, id_ingrediente) VALUES
(1, 3), -- Guanciale for Spaghetti Carbonara
(1, 4), -- Uova for Spaghetti Carbonara
(1, 5), -- Pecorino for Spaghetti Carbonara
(1, 6), -- Pepe for Spaghetti Carbonara
(2, 1), -- Pomodoro for Pizza Margherita
(2, 2), -- Mozzarella for Pizza Margherita
(2, 7), -- Basilico for Pizza Margherita
(3, 8), -- Caffè for Tiramisù
(3, 9), -- Savoiardi for Tiramisù
(3, 10), -- Mascarpone for Tiramisù
(4, 11), -- Uva for Vino Rosso
(5, 12), -- Luppolo for Birra
(7, 13); -- Arancia for Succo d'Arancia
