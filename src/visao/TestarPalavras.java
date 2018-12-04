package visao;

import java.net.URL;
import java.util.ResourceBundle;

import core.App;
import core.Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class TestarPalavras implements Initializable {

	@FXML
	private Pane pane;

	@FXML
	private Label lbTitulo;

	@FXML
	private TextField tfPalavra;

	@FXML
	private Button btnTestar;

	@FXML
	private Label lbResultado;

	@FXML
	private Label lbPalavraTestada;

	@FXML
	private TextField tfPalavraTestada;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbResultado.setText("");
		lbPalavraTestada.setVisible(false);
		tfPalavra.setOnKeyReleased(key -> {
			if (key.getCode() == KeyCode.ENTER)
				testar();
		});
		btnTestar.setOnAction(e -> testar());
	}

	private void testar() {
		lbPalavraTestada.setVisible(true);
		String palavra = tfPalavra.getText();
		tfPalavraTestada.setVisible(true);
		tfPalavraTestada.setText(palavra);
		try {
			boolean aceita = App.getAutomato().testaPalavra(palavra);
			tfPalavra.clear();
			if (aceita) {
				lbResultado.setText("PALAVRA ACEITA");
				lbResultado.setTextFill(Paint.valueOf("#4dff00"));
			} else {
				lbResultado.setText("PALAVRA REJEITADA");
				lbResultado.setTextFill(Paint.valueOf("#ff0000"));
			}
		} catch (Exception e) {
			Util.mostrarErro("Erro!", "Um erro ocorreu ao testar a palavra!");
		}
	}

}
