package visao;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.App;
import core.Util;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.Renderer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

	private Stage testarPalavras;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		testarPalavras = criarTestarPalavras();
		imagem = Graphviz.fromString(App.getAutomato().toDot()).width(1920).height(1080).render(Format.PNG);
		ivImagem.setImage(SwingFXUtils.toFXImage(imagem.toImage(), null));
		ivImagem.fitWidthProperty().bind(vbox.widthProperty());
		ivImagem.fitHeightProperty().bind(vbox.heightProperty().subtract(30));
		ivImagem.setPreserveRatio(true);
		miConverterAFD.setOnAction(e -> converter());
		miSalvarImagem.setOnAction(e -> salvarImagem());
		miSalvarImagemComo.setOnAction(e -> salvarImagemComo());
		miFechar.setOnAction(e -> {
			try {
				testarPalavras.hide();
				App.carregarAbrirArquivo(getClass().getResourceAsStream("/AbrirArquivo.fxml"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		miAbrir.setOnAction(e -> {
			try {
				testarPalavras.hide();
				App.carregarAbrirArquivo(getClass().getResourceAsStream("/AbrirArquivo.fxml"));
				AbrirArquivo.escolherArquivo(getClass().getResourceAsStream("/TelaPrincipal.fxml"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		miSair.setOnAction(e -> Platform.exit());
		miTestarPalavras.setOnAction(e -> mostrarTestarPalavras());
	}

	private void mostrarTestarPalavras() {
		testarPalavras.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		testarPalavras.setX((primScreenBounds.getWidth() - testarPalavras.getWidth()) / 2);
		testarPalavras.setY((primScreenBounds.getHeight() - testarPalavras.getHeight()) / 2);
	}

	private Stage criarTestarPalavras() {
		FXMLLoader loader = new FXMLLoader();
		Parent layout = null;
		try {
			layout = loader.load(getClass().getResourceAsStream("/TestarPalavras.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(layout);
		Stage stage = new Stage();
		stage.resizableProperty().set(false);
		stage.setAlwaysOnTop(true);
		stage.initOwner(App.getMainStage());
		stage.setTitle("Testar Palavras");
		stage.setScene(scene);
		return stage;
	}

	private void converter() {
		App.getAutomato().converteAFNparaAFD();
		imagem = Graphviz.fromString(App.getAutomato().toDot()).width(1920).height(1080).render(Format.PNG);
		ivImagem.setImage(SwingFXUtils.toFXImage(imagem.toImage(), null));
		miConverterAFD.setDisable(true);
	}

	private void salvarImagem() {
		salvar(new File("automato.png"));
	}

	private void salvarImagemComo() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Imagem do Autômato", "*.png"));
		File arquivo = fileChooser.showSaveDialog(App.getMainStage());
		salvar(arquivo);
	}

	private void salvar(File arquivo) {
		if (arquivo == null)
			return;
		try {
			imagem.toFile(arquivo);
			Util.mostrarMensagem("Imagem salva!",
					"A imagem do autômato foi salva com sucesso em '" + arquivo.getAbsolutePath() + "'.");
		} catch (IOException e) {
			Util.mostrarErro("Erro!", "Um erro ocorreu ao salvar a imagem!");
			e.printStackTrace();
		}
	}

}
