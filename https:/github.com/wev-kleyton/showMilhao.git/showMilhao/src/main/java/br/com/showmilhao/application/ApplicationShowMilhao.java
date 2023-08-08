package br.com.showmilhao.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ApplicationShowMilhao extends Application {

	private static Stage stage;
	private static final String FILE_MUSIC = "src/main/resources/songs/som-abertura-2.mp3";
	private static final String FILE_MUSIC_CARTA = "src/main/resources/songs/tire-a-carta-do-baralho-voice.mp3";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			primaryStage.setTitle("Show do Milhão");
			Pane telaInicial = FXMLLoader.load(getClass().getResource("/view/LayoutTelaInicial.fxml"));
			Scene scene = new Scene(telaInicial, 800, 600);
			telaInicial.getStylesheets().add(getClass().getResource("/css/buttonStyle.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

			ContinousReprodution reprodution = new ContinousReprodution(FILE_MUSIC, true);
			reprodution.start();

//			Connection connection = ConnectionFasctory.getConnection();
//			String sql = "INSERT INTO jogador (id, nome, pontuacao) VALUES ($next_id,?,?)";
//			try (PreparedStatement statement = connection.prepareStatement(sql)) {
//				statement.setString(2, "Predro");
//				statement.setInt(3, 200);
//				statement.execute();
//				connection.commit();
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScene(Scene scene) {
		stage.setScene(scene);
	}
}
