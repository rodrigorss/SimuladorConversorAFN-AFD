package visao;

import java.net.URL;
import java.util.ResourceBundle;

import core.App;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TelaPrincipal implements Initializable {

	@FXML
	private VBox vbox;

	@FXML
	private MenuBar menubar;

	@FXML
	private Menu menuArquivo;

	@FXML
	private MenuItem miAbrir;

	@FXML
	private MenuItem miFechar;

	@FXML
	private MenuItem miSalvarImagem;

	@FXML
	private MenuItem miSalvarImagemComo;

	@FXML
	private MenuItem miSair;

	@FXML
	private Menu menuOpcoes;

	@FXML
	private MenuItem miConverterAFD;

	@FXML
	private MenuItem miTestarPalavras;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView ivImagem;

	private Renderer imagem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imagem = Graphviz.fromString(App.getAutomato().toDot()).width(1920).height(1080).render(Format.PNG);
		ivImagem.setImage(SwingFXUtils.toFXImage(imagem.toImage(), null));
		ivImagem.fitWidthProperty().bind(vbox.widthProperty());
		ivImagem.fitHeightProperty().bind(vbox.heightProperty().subtract(30));
		ivImagem.setPreserveRatio(true);
		miConverterAFD.setOnAction(e -> {
			converter();
		});
	}

	private void converter() {
		App.getAutomato().converteAFNparaAFD();
		imagem = Graphviz.fromString(App.getAutomato().toDot()).width(1920).height(1080).render(Format.PNG);
		ivImagem.setImage(SwingFXUtils.toFXImage(imagem.toImage(), null));
		miConverterAFD.setDisable(true);
	}

}
