	DROP DATABASE IF EXISTS banca;

	CREATE DATABASE banca;

	USE banca;

	CREATE TABLE Conti (
		numero_conto INT PRIMARY KEY auto_increment,
		iban VARCHAR(255) UNIQUE,
		saldo DECIMAL(10, 2),
		banca VARCHAR(255)
	);

	CREATE TABLE Carta (
		numero_carta INT PRIMARY KEY,
		numero_conto INT UNIQUE,
		tipo VARCHAR(255),
		FOREIGN KEY (numero_conto) REFERENCES Conti(numero_conto)
	);

	CREATE TABLE Clienti (
		id_cliente INT PRIMARY KEY AUTO_INCREMENT,
		pin VARCHAR(255),
		codice_cliente VARCHAR(255) UNIQUE
	);

	CREATE TABLE Transazioni (
		id_transazione INT PRIMARY KEY AUTO_INCREMENT,
		causale VARCHAR(300),
		importo DECIMAL(10, 2),
		data_transazione DATE,
		codice_cliente VARCHAR(255),
		codice_destinatario VARCHAR(255),
		FOREIGN KEY (codice_cliente) REFERENCES Clienti(codice_cliente),
		FOREIGN KEY (codice_destinatario) REFERENCES Clienti(codice_cliente)
	);

	CREATE TABLE ContiUtente (
		numero_conto INT,
		id_cliente INT,
		FOREIGN KEY (numero_conto) REFERENCES Conti(numero_conto),
		FOREIGN KEY (id_cliente) REFERENCES Clienti(id_cliente)
	);

	INSERT INTO Conti (numero_conto, iban, saldo, banca) VALUES
	(100000000, 'IT60X1234567890123456789012', 1000.00, 'Banca A'),
	(100000001, 'IT60Y1234567890123456789013', 1500.50, 'Banca B');

	INSERT INTO Clienti (pin, codice_cliente) VALUES
	('1234', 'C1'),
	('5678', 'C2');

	INSERT INTO Carta (numero_carta, numero_conto, tipo) VALUES
	(1, 100000000, 'Credito'),  -- Updated to match the correct numero_conto
	(2, 100000001, 'Debito');    -- Upd

	select * from Conti;

	select * from clienti;

	select * from ContiUtente;