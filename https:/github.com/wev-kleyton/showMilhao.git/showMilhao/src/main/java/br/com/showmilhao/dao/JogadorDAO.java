package br.com.showmilhao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.showmilhao.connection.ConnectionFasctory;
import br.com.showmilhao.model.Jogador;
import br.com.showmilhao.util.LogUtil;

public class JogadorDAO {

	private Connection connection;

	private final static String QUERY_INSERT_JOGADOR = "INSERT INTO jogador (id, nome, pontuacao) VALUES ($next_id, ?, ?)";
	private final static String QUERY_ATUALIZA_JOGADOR = "UPDATE jogador SET nome = ? , pontuacao = ? WHERE id = ?";
	private final static String QUERY_CONSULTAR_TODOS = "SELECT * FROM jogador";
	private final static String QUERY_LISTAR_RANKING = "SELECT * FROM jogador ORDER BY pontuacao DESC LIMIT 10";
	private final static String QUERY_ZERA_RANKING = "DELETE FROM jogador";

	public JogadorDAO() {
		connection = ConnectionFasctory.getConnection();
	}

	public boolean adicionar(Jogador jogador) {
		String sql = QUERY_INSERT_JOGADOR;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(2, jogador.getNome());
			stmt.setInt(3, jogador.getPontuacao());
			stmt.executeUpdate();
			connection.commit();
			return true;
		} catch (Exception e) {
			LogUtil.getLogger(JogadorDAO.class).error(e.getCause().toString());
			return false;
		}

	}

	public void atualizar(Jogador jogador) {
		String sql = QUERY_ATUALIZA_JOGADOR;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, jogador.getNome());
			stmt.setInt(2, jogador.getPontuacao());
			stmt.setInt(3, jogador.getId());
			stmt.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			LogUtil.getLogger(JogadorDAO.class).error(e.getCause().toString());
		}
	}

	private List<Jogador> buscar(String sql) {
		List<Jogador> jogadores = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Jogador jogador = new Jogador();
					jogador.setId(rs.getInt("id"));
					jogador.setLinha(rs.getRow());
					jogador.setNome(rs.getString("nome"));
					jogador.setPontuacao(rs.getInt("pontuacao"));
					jogadores.add(jogador);
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(JogadorDAO.class).error(e.getCause().toString());
		}
		return jogadores;
	}

	public List<Jogador> listar() {
		return buscar(QUERY_CONSULTAR_TODOS);
	}

	public List<Jogador> listarRanking() {
		return buscar(QUERY_LISTAR_RANKING);
	}

	public void zerarRanking() {
		String sql = QUERY_ZERA_RANKING;
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			LogUtil.getLogger(JogadorDAO.class).error(e.getCause().toString());
		}
	}
}
