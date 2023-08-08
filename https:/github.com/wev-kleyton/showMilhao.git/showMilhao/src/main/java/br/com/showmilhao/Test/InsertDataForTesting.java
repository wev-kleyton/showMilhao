package br.com.showmilhao.Test;

import br.com.showmilhao.dao.PerguntaDAO;
import br.com.showmilhao.model.Pergunta;

public class InsertDataForTesting {

//	private static JogadorDAO jogadorDAO = new JogadorDAO();
	private static PerguntaDAO perguntaDAO = new PerguntaDAO();

	public static void main(String[] args) {

//		Jogador jogador = new Jogador("Ana Paula", 800);
//		System.out.println(jogadorDAO.atualizar(jogador));

//		Jogador jogador = new Jogador();
//		jogador.setId(1);
//		jogador.setNome("wev kleyton");
//		jogador.setPontuacao(1001);
//
//		jogadorDAO.atualizar(jogador);
		
//		jogadorDAO.listar().forEach(j -> System.out.println(j.getNome()));
//		jogadorDAO.listar().forEach(System.out::println);
		
//		jogadorDAO.zerarRanking();
//		jogadorDAO.listarRanking().forEach(System.out::println);
		
//		Pergunta pergunta = new Pergunta();
//		pergunta.setNivel("dificil");
//		pergunta.setEnunciado("Onde foi conduzida a vitória das forças aliadas na Segunda Guerra Muldial?");
//		pergunta.setAlternativa1("CANNES");
//		pergunta.setAlternativa2("CAPRI");
//		pergunta.setAlternativa3("MARSELHA");
//		pergunta.setResposta("NORMANDIA");
//		pergunta.setId(10);
//		perguntaDAO.atualizar(pergunta);
//		perguntaDAO.adicionar(pergunta);
		
//		perguntaDAO.remover(2);
		
		perguntaDAO.listar("","dificil").forEach(System.out::println);
		
	}

}
