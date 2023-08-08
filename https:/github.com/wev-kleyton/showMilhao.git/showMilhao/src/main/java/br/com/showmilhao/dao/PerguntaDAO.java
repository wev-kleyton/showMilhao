package br.com.showmilhao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.com.showmilhao.connection.ConnectionFasctory;
import br.com.showmilhao.model.Pergunta;
import br.com.showmilhao.util.LogUtil;

public class PerguntaDAO {

	private static final String ORDER_BY_RANDOM_LIMIT = "ORDER BY RANDOM() LIMIT 1";
	private static final String QUERY_INSERT = "INSERT INTO perguntas (id, nivel, enunciado, alternativa1, alternativa2, alternativa3, resposta) VALUES ($next_id, ?, ?, ?, ?, ?, ?)";
	private static final String QUERY_UPDATE = "UPDATE perguntas SET nivel= ? ,enunciado=? ,alternativa1= ?, alternativa2= ?, alternativa3= ?, resposta =? WHERE id = ?";
	private static final String QUERY_DELETE = "DELETE FROM perguntas WHERE id = ? ";
	private static final String QUERY_SELECT_ALL = "SELECT * FROM perguntas";
	private static final String QUERY_SELECT_POR_NIVEL = "SELECT * FROM perguntas WHERE nivel = ?";
	private static final String QUERY_SELECT_NIVEL_RANDOM_LIMIT = "SELECT * FROM perguntas WHERE nivel = ?"
			+ ORDER_BY_RANDOM_LIMIT;
	private static final String QUERY_SELECT_NIVEL_RANDOM_LIMIT_PERGUNTAS_FEITAS = "SELECT * FROM perguntas WHERE nivel = ? AND perguntas.id NOT IN ";
	private static final int MESSAGE_TYPE = JOptionPane.INFORMATION_MESSAGE;
	private static final String OK = "Processo Concluido";

	private Connection connecton;

	public PerguntaDAO() {
		connecton = ConnectionFasctory.getConnection();
	}

	public void adicionar(Pergunta pergunta) {
		String sql = QUERY_INSERT;
		try (PreparedStatement stmt = connecton.prepareStatement(sql)) {
			stmt.setString(2, pergunta.getNivel());
			stmt.setString(3, pergunta.getEnunciado());
			stmt.setString(4, pergunta.getAlternativa1());
			stmt.setString(5, pergunta.getAlternativa2());
			stmt.setString(6, pergunta.getAlternativa3());
			stmt.setString(7, pergunta.getResposta());
			stmt.executeUpdate();
			connecton.commit();
		} catch (Exception e) {
			LogUtil.getLogger(PerguntaDAO.class).error(e.getCause().toString());
		}
		JOptionPane.showMessageDialog(new JFrame(), "Pergunta Adicionada com Sucesso!", OK, MESSAGE_TYPE);
	}

	public void atualizar(Pergunta pergunta) {
		String sql = QUERY_UPDATE;
		try (PreparedStatement stmt = connecton.prepareStatement(sql)) {
			stmt.setString(1, pergunta.getNivel());
			stmt.setString(2, pergunta.getEnunciado());
			stmt.setString(3, pergunta.getAlternativa1());
			stmt.setString(4, pergunta.getAlternativa2());
			stmt.setString(5, pergunta.getAlternativa3());
			stmt.setString(6, pergunta.getResposta());
			stmt.setInt(7, pergunta.getId());
			stmt.executeUpdate();
			connecton.commit();
		} catch (Exception e) {
			LogUtil.getLogger(PerguntaDAO.class).error(e.getCause().toString());
		}
		JOptionPane.showMessageDialog(new JFrame(), "Alterações Realizadas com Sucesso!", OK, MESSAGE_TYPE);
	}

	public void remover(Integer idPergunta) {
		try (PreparedStatement stmt = connecton.prepareStatement(QUERY_DELETE)) {
			stmt.setInt(1, idPergunta);
			stmt.execute();
			connecton.commit();
			JOptionPane.showMessageDialog(new JFrame(), "Pergunta Removida com Sucesso!", OK, MESSAGE_TYPE);

		} catch (Exception e) {
			LogUtil.getLogger(PerguntaDAO.class).error(e.getCause().toString());
		}
	}

	private List<Pergunta> buscar(String sql, String nivel) {
		List<Pergunta> perguntas = new ArrayList<>();
		try (PreparedStatement stmt = connecton.prepareStatement(sql)) {
			if (Objects.nonNull(nivel))
				stmt.setString(1, nivel);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Pergunta pergunta = new Pergunta();
					pergunta.setId(rs.getInt("id"));
					pergunta.setNivel(rs.getString("nivel"));
					pergunta.setEnunciado(rs.getString("enunciado"));
					pergunta.setAlternativa1(rs.getString("alternativa1"));
					pergunta.setAlternativa2(rs.getString("alternativa2"));
					pergunta.setAlternativa3(rs.getString("alternativa3"));
					pergunta.setResposta(rs.getString("resposta"));
					perguntas.add(pergunta);
				}
			}
		} catch (Exception e) {
			LogUtil.getLogger(PerguntaDAO.class).error(e.getCause().toString());
		}
		return perguntas;
	}

	public List<Pergunta> listar() {
		return buscar(QUERY_SELECT_ALL, null);
	}

	public List<Pergunta> listar(String nivel) {
		return buscar(QUERY_SELECT_POR_NIVEL, nivel);
	}

	public List<Pergunta> listar(String idPerguntasFeitas, String nivel) {
		String sql = idPerguntasFeitas.isEmpty() ? QUERY_SELECT_NIVEL_RANDOM_LIMIT
				: QUERY_SELECT_NIVEL_RANDOM_LIMIT_PERGUNTAS_FEITAS + idPerguntasFeitas + ORDER_BY_RANDOM_LIMIT;
		return buscar(sql, nivel);
	}
}
