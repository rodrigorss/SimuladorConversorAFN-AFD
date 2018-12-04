package visao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import core.App;
import core.Automato;
import core.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;

public class AbrirArquivo implements Initializable {

	@FXML
	private Button btnAbrirArquivo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAbrirArquivo.setOnAction(e -> escolherArquivo(getClass().getResourceAsStream("/TelaPrincipal.fxml")));

	}

	public static void escolherArquivo(InputStream arquivoTela) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivo de Entrada", "*.txt"));
		try {
			File arquivo = fileChooser.showOpenDialog(App.getMainStage());
			Automato aut = Automato.lerAutomato(arquivo);
			App.setAutomato(aut);
		} catch (Exception e) {
			e.printStackTrace();
			Util.mostrarErro("Erro!",
					"Não foi possível carregar o arquivo! Por favor, verifique se ele está no formato adequado.");
		}
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent layout = loader.load(arquivoTela);
			Scene scene = new Scene(layout);
			App.getMainStage().setScene(scene);
			App.getMainStage().resizableProperty().set(true);
			App.getMainStage().show();
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			App.getMainStage().setX((primScreenBounds.getWidth() - App.getMainStage().getWidth()) / 2);
			App.getMainStage().setY((primScreenBounds.getHeight() - App.getMainStage().getHeight()) / 2);
		} catch (IOException e) {
			e.printStackTrace();
			Util.mostrarErro("Erro!", "Um erro ocorreu! Não foi possível carregar o programa.");
		}
	}

}
