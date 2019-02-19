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
	private Pane anchor, segundoPane;
	private static DashboardFX INSTANCE;
	
	public static DashboardFX getInstance() {
		return INSTANCE;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		INSTANCE = this;
	}
	
	@FXML
	private void acessarTelaProjetos(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("projeto.fxml"));
		setPane(segundoPane);
	}

	@FXML
	private void acessarTelaRequisitos(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("requisito.fxml"));
		setPane(segundoPane);
	}

	@FXML
	private void acessarTelaDesenvolvedores(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("desenvolvedor.fxml"));
		setPane(segundoPane);
	}

	@FXML
	private void acessarTelaPlanosTeste(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("planoTeste.fxml"));
		setPane(segundoPane);
	}

	@FXML
	private void acessarTelaStakeholders(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("stakeholder.fxml"));
		setPane(segundoPane);
	}

	@FXML
	private void acessarTelaBugs(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("projeto.fxml"));
		setPane(segundoPane);
	}

	@FXML
	private void acessarTelaSprints(MouseEvent event) throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("sprint.fxml"));
		setPane(segundoPane);
	}
	
	public void acessarTelaVincularRequisitoSprint() throws IOException {
		segundoPane = FXMLLoader.load(getClass().getResource("vincularRequisitoSprint.fxml"));
		setPane(segundoPane);
	}

	private void setPane(Pane pane) {
		List<Node> parentChildren = ((Pane) anchor.getParent()).getChildren();
		parentChildren.set(parentChildren.indexOf(anchor), pane);

		anchor = pane;
	}

}
