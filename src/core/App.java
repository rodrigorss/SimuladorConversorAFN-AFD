package core;

import java.io.IOException;
import java.io.InputStream;

import guru.nidi.graphviz.engine.Graphviz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage mainStage;
	private static Automato automato;

	public static void main(String[] args) {
		Graphviz.useDefaultEngines();
		launch();
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		App.mainStage = primaryStage;
		carregarAbrirArquivo(getClass().getResourceAsStream("/AbrirArquivo.fxml"));
	}

	public static void setAutomato(Automato automato) {
		App.automato = automato;
	}

	public static Automato getAutomato() {
		return automato;
	}

	public static void carregarAbrirArquivo(InputStream arquivo) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent layout = loader.load(arquivo);
		Scene scene = new Scene(layout);
		Stage stage = App.mainStage;
		stage.resizableProperty().set(false);
		stage.setTitle("Simulador e Conversor de AFN para AFD");
		stage.setScene(scene);
		stage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
	}

}
