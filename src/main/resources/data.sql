CREATE TABLE IF NOT EXISTS conta (
    id_conta BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_responsavel VARCHAR(80) UNIQUE NOT NULL,
    numero_conta INT UNIQUE NOT NULL
    
);

CREATE TABLE IF NOT EXISTS saldo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    saldo DOUBLE
);

ALTER TABLE conta ADD COLUMN saldo_id BIGINT;
ALTER TABLE conta ADD CONSTRAINT FK_CONTA_SALDO FOREIGN KEY (saldo_id) REFERENCES saldo(id);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(55) UNIQUE NOT NULL,
    username VARCHAR(55),
    password VARCHAR(150),
    email VARCHAR(55) UNIQUE NOT NULL,
    role VARCHAR(30),
    conta_id BIGINT,
    FOREIGN KEY (conta_id) REFERENCES conta(id_conta)
    
);


CREATE TABLE transferencia
(
    id IDENTITY NOT NULL PRIMARY KEY,
    data_transferencia TIMESTAMP WITH TIME ZONE NOT NULL,
    valor NUMERIC (20,2) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    nome_operador_transacao VARCHAR (50),
    conta_id INT,

        CONSTRAINT FK_CONTA
        FOREIGN KEY (conta_id)
        REFERENCES conta(id_conta)
);

CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_transacao TIMESTAMP,
    saldo DOUBLE,
    conta_id_conta BIGINT,
    transferencia_id BIGINT,
    FOREIGN KEY (conta_id_conta) REFERENCES conta(id_conta),
    FOREIGN KEY (transferencia_id) REFERENCES transferencia(id)
);


INSERT INTO saldo (id, saldo) VALUES (1, 0.0);
INSERT INTO saldo (id, saldo) VALUES (2, 0.0);
INSERT INTO conta (id_conta, nome_responsavel, numero_conta, saldo_id) VALUES (1,'Fulano', 12345, 1);
INSERT INTO conta (id_conta, nome_responsavel, numero_conta, saldo_id) VALUES (2,'Sicrano', 54321, 2);
INSERT INTO usuario ( name, username, password, email, role, conta_id) VALUES ( 'Fulano', 'fulano', '$2a$10$/u.82nDXKL7xV6ZIal7CDOW5iZN9jgLAl3dg0h2vnWNddeMZog/K2', 'fulano@gmail.com', 'USER', 1);
INSERT INTO usuario ( name, username, password, email, role, conta_id) VALUES ( 'Sicrano', 'sicrano', '$2a$10$Y/sC3uoA/uORDvkvI5chPebgKY9UQoetR/L1Sw/GZvFBkwEwKnH/.', 'sicrano@gmail.com', 'USER', 2);

INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (1,'2019-01-01 ',30895.46,'DEPOSITO', null, 1);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (2,'2019-02-03',12.24,'DEPOSITO', null,2);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (3,'2019-05-04 ',-500.50,'SAQUE', null,1);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (4,'2019-08-07 ',-530.50,'SAQUE', null,2);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (5,'2020-06-08 ',3241.23,'TRANSFERENCIA', 'Beltrano',1);
INSERT INTO transferencia (id,data_transferencia, valor, tipo, nome_operador_transacao, conta_id) VALUES (6,'2021-04-01 ',25173.09,'TRANSFERENCIA', 'Ronnyscley',2);
INSERT INTO transaction (id, data_transacao, saldo, conta_id_conta, transferencia_id) VALUES (1, '2019-01-01 12:00:00+03', 500, 1, 1);
INSERT INTO transaction (id, data_transacao, saldo, conta_id_conta, transferencia_id) VALUES (2, '2019-01-01 12:00:00+03', 12.24, 1, 2);
INSERT INTO transaction (id, data_transacao, saldo, conta_id_conta, transferencia_id) VALUES (3, '2019-01-01 12:00:00+03', -500, 2, 3);
INSERT INTO transaction (id, data_transacao, saldo, conta_id_conta, transferencia_id) VALUES (4, '2019-01-01 12:00:00+03', -530.50, 2, 4);
