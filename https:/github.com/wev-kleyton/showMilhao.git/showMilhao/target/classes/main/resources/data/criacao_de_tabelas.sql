CREATE TABLE jogador (
	id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	nome VARCHAR (30) NOT NULL,
	pontuacao INTEGER (10) DEFAULT (0)
);

CREATE TABLE perguntas (
	id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,
	nivel VARCHAR (10) NOT NULL,
	enunciado VARCHAR (500) NOT NULL,
	alternativa1 VARCHAR (50) NOT NULL,
	alternativa2 VARCHAR (50) NOT NULL,
	alternativa3 VARCHAR (50) NOT NULL,
	resposta VARCHAR (50) NOT NULL
);

select * from jogador;
update jogador set nome = 'wev', pontuacao = 500 where id = 1;
