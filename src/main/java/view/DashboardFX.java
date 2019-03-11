package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DashboardFX implements Initializable {

	@FXML
	public Pane anchor, segundoPane;
	public static DashboardFX INSTANCE;

	public static DashboardFX getInstance() {
		return INSTANCE;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		INSTANCE = this;
	}

	@FXML
	public void acessarTelaProjetos(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("projeto.fxml"));
		setPane(segundoPane);
	}

	@FXML
	public void acessarTelaRequisitos(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("requisito.fxml"));
		setPane(segundoPane);
	}

	@FXML
	public void acessarTelaDesenvolvedores(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("desenvolvedor.fxml"));
		setPane(segundoPane);
	}

	@FXML
	public void acessarTelaPlanosTeste(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("planoTeste.fxml"));
		setPane(segundoPane);
	}

	@FXML
	public void acessarTelaStakeholders(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("stakeholder.fxml"));
		setPane(segundoPane);
	}

	@FXML
	public void acessarTelaBugs(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("bug.fxml"));
		setPane(segundoPane);
	}

	@FXML
	public void acessarTelaSprints() throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("sprint.fxml"));
		setPane(segundoPane);
	}

	public void acessarTelaVincularRequisitoSprint() throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("vincularRequisitoSprint.fxml"));
		setPane(segundoPane);
	}

	public void acessarTelaVincularDesenvolvedorRequisitoNaSprint() throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("vincularDesenvolvedorRequisitoNaSprint.fxml"));
		setPane(segundoPane);
	}

	public void acessarTelaIndicarStatusCasosTeste() throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("indicarStatusCasosTeste.fxml"));
		setPane(segundoPane);
	}

	public void acessarTelaPredicao() throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("predicaoTeste.fxml"));
		setPane(segundoPane);
	}

	public void setPane(Pane pane) {
		List<Node> parentChildren = ((Pane) anchor.getParent()).getChildren();
		parentChildren.set(parentChildren.indexOf(anchor), pane);

		anchor = pane;
	}

}
