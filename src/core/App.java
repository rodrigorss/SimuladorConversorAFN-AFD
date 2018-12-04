package core;

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
		FXMLLoader loader = new FXMLLoader();
		Parent layout = loader.load(getClass().getResourceAsStream("/AbrirArquivo.fxml"));
		Scene scene = new Scene(layout);
		mainStage = primaryStage;
		primaryStage.resizableProperty().set(false);
		primaryStage.setTitle("Simulador e Conversor de AFN para AFD");
		primaryStage.setScene(scene);
		primaryStage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	}

	public static void setAutomato(Automato automato) {
		App.automato = automato;
	}

	public static Automato getAutomato() {
		return automato;
	}

}
