package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainFX implements Initializable {

	@FXML
	private Pane anchor;
	private Pane secondAnchor;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	public void acessarProjeto(MouseEvent event) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("projeto.fxml"));
		setPane(secondAnchor);
	}

	@FXML
	public void acessarStakeholder(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("stakeholder.fxml"));
		setPane(secondAnchor);
	}

	@FXML
	public void acessarRequisitos(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("requisito.fxml"));
		setPane(secondAnchor);
	}

	@FXML
	public void acessarDesenvolvedor(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("desenvolvedor.fxml"));
		setPane(secondAnchor);

	}

	@FXML
	public void acessarTarefa(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("tarefa.fxml"));
		setPane(secondAnchor);
	}

	@FXML
	public void acessarBugs(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("bug.fxml"));
		setPane(secondAnchor);
	}

	@FXML
	public void acessarRelease(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("release.fxml"));
		setPane(secondAnchor);
	}

	@FXML
	public void acessarPlanoTeste(Event e) throws IOException {
		secondAnchor = FXMLLoader.load(getClass().getResource("planoTeste.fxml"));
		setPane(secondAnchor);
	}
	
	private void setPane(Pane pane) {
		List<Node> parentChildren = ((Pane) anchor.getParent()).getChildren();
		parentChildren.set(parentChildren.indexOf(anchor), pane);

		anchor = pane;
		anchor.setLayoutX(210.0);
		anchor.setLayoutY(7.0);
		anchor.setPrefHeight(562.0);
		anchor.setPrefWidth(562.0);
	}
}
