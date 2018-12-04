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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
			if (arquivo == null)
				return;
			Automato aut = Automato.lerAutomato(arquivo);
			App.setAutomato(aut);
			FXMLLoader loader = new FXMLLoader();
			Parent layout = loader.load(arquivoTela);
			Scene scene = new Scene(layout);
			App.getMainStage().setScene(scene);
			App.getMainStage().resizableProperty().set(true);
			App.getMainStage().setMaximized(true);
			App.getMainStage().show();
		} catch (IOException e) {
			e.printStackTrace();
			Util.mostrarErro("Erro!", "Um erro ocorreu! Não foi possível carregar o programa.");

		} catch (Exception e) {
			e.printStackTrace();
			Util.mostrarErro("Erro!",
					"Não foi possível carregar o arquivo! Por favor, verifique se ele está no formato adequado.");
		}
	}

}
