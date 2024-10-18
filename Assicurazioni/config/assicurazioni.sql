drop database if exists assicurazioni;

create database if not exists assicurazioni;
use assicurazioni;

create table if not exists clienti(
	id_cliente int not null auto_increment,
	codice_fiscale varchar(255),
	data_nascita date,
	nome varchar(255),
	cognome varchar(255),
	primary key(id_cliente)
); 

create table if not exists risarcimenti(
	id_risarcimento int not null auto_increment,
	importo int,
	primary key(id_risarcimento)
);

create table if not exists beni(
	id_bene int not null auto_increment,
	tipo varchar(255),
	primary key(id_bene)
);

create table if not exists polizze(
	id_polizza int not null auto_increment,
	perc_copertura int,
	prezzo int,
	inizio_validita date,
	fine_validita date,
	stato boolean,
	id_cliente int, 
	id_risarcimento int,
	id_bene int,
	primary key(id_polizza),
	foreign key(id_risarcimento) references risarcimenti(id_risarcimento),
	foreign key(id_bene) references beni(id_bene),
	foreign key(id_cliente) references clienti(id_cliente)
);