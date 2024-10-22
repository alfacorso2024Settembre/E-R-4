drop database if exists fitness;
create database if not exists fitness;
use fitness;
create table if not exists utenti(
	id_utente int not null primary key auto_increment,
	nome_utente varchar(80),
	cf varchar(20) unique
);

create table if not exists tipi_attivita(
	id_tipo_attivita int not null primary key auto_increment,
	nome_tipo varchar(80),
	descrizione varchar(255)
);

create table if not exists obiettivi(
	id_obiettivo int not null primary key auto_increment,
	descrizione varchar(255),
	data_inizio date not null,
	data_fine date not null,
	progresso_attuale int,
	progresso_totale int,
	stato_completato boolean,
	id_utente int,
	foreign key (id_utente) references utenti(id_utente)
);

create table if not exists attivita_fisiche(
	id_attivita int not null primary key auto_increment,
	data_attivita date not null,
	id_utente int,
	foreign key(id_utente) references utenti(id_utente)

);


create table if not exists gestione_attivita(
	id_attivita int,
	id_tipo_attivita int,
	foreign key(id_attivita) references attivita_fisiche(id_attivita),
	foreign key (id_tipo_attivita) references tipi_attivita(id_tipo_attivita),
	primary key(id_attivita,id_tipo_attivita)
);
INSERT INTO utenti (nome_utente, cf) VALUES
('Mario Rossi', 'RSSMRA85M01H501Z'),
('Lucia Bianchi', 'BNCUCA90F41H501Y'),
('Giovanni Verdi', 'VRDGVN75B01H501X');

-- Populate tipi_attivita table
INSERT INTO tipi_attivita (nome_tipo, descrizione) VALUES
('Corsa', 'Attività di corsa all\'aperto o su tapis roulant'),
('Palestra', 'Esercizi di resistenza e forza in palestra'),
('Yoga', 'Attività per migliorare la flessibilità e la concentrazione'),
('Nuoto', 'Esercizi in acqua per migliorare resistenza e forza');

-- Populate obiettivi table
INSERT INTO obiettivi (descrizione, data_inizio, data_fine, progresso_attuale, progresso_totale, stato_completato, id_utente) VALUES
('Perdere peso', '2024-01-01', '2024-06-01', 5, 10, FALSE, 1),
('Aumentare la massa muscolare', '2024-01-15', '2024-12-15', 3, 8, FALSE, 2),
('Migliorare la flessibilità', '2024-02-01', '2024-05-01', 2, 5, FALSE, 3);


