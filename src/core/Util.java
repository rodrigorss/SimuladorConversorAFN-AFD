package core;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {

	public static void mostrarErro(String titulo, String mensagem) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titulo);
		alert.setHeaderText(titulo);
		alert.setContentText(mensagem);
		alert.showAndWait();
	}

}
