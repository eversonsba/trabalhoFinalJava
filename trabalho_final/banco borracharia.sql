DROP DATABASE IF EXISTS borracharia;
CREATE DATABASE IF NOT EXISTS borracharia;
USE borracharia;

CREATE TABLE IF NOT EXISTS clientes(
	cnpj VARCHAR(14),
	nome varchar(255) NOT NULL,
	PRIMARY KEY(cnpj)
);

CREATE TABLE IF NOT EXISTS funcionario_cliente(
id INT AUTO_INCREMENT,
nome VARCHAR(255),
cnpj_cliente VARCHAR(255),
PRIMARY KEY(id),
FOREIGN KEY(cnpj_cliente)
REFERENCES clientes(cnpj)
);

CREATE TABLE IF NOT EXISTS complementos(
id INT AUTO_INCREMENT,
descricao TEXT,
PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS servico(
id INT AUTO_INCREMENT,
descricao TEXT,
valor FLOAT,
data DATE,
cnpj_cliente VARCHAR(14) NOT NULL,
id_complemento INT,
id_funcionario_cliente INT,

PRIMARY KEY (id),

FOREIGN KEY(cnpj_cliente)
REFERENCES clientes(cnpj),

FOREIGN KEY(id_complemento)
REFERENCES complementos(id),

FOREIGN KEY(id_funcionario_cliente)
REFERENCES funcionario_cliente(id)
);

CREATE TABLE IF NOT EXISTS telefones(
id INT AUTO_INCREMENT NOT NULL,
numero VARCHAR(11) NOT NULL,
cnpj_cliente VARCHAR(14),

FOREIGN KEY(cnpj_cliente)
REFERENCES clientes(cnpj),


PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS enderecos(
id INT AUTO_INCREMENT,
numero INT,
rua VARCHAR(255) NOT NULL,
cidade VARCHAR(255) NOT NULL,
cnpj_cliente VARCHAR(14),

FOREIGN KEY(cnpj_cliente)
REFERENCES clientes(cnpj),

PRIMARY KEY (id)
);
