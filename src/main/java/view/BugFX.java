package view;

import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.DesenvolvedorController;
import controller.SprintController;
import controller.BugController;
import controller.BugController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Desenvolvedor;
import model.Sprint;
import model.Bug;
import model.Bug;

public class BugFX implements Initializable {

	@FXML
	private TextField titulo;
	@FXML
	private ComboBox<String> selectDesenvolvedor;
	@FXML
	private ComboBox<String> selectPrioridade;
	@FXML
	private ComboBox<String> selectImpacto;
	@FXML
	private ComboBox<String> selectRelease;
	@FXML
	private Button btnSalvar;
	@FXML
	private TableView<Bug> listaBugs;
	@FXML
	private TableColumn<Bug, Integer> listaId;
	@FXML
	private TableColumn<Bug, String> listaTitulo, listaDescricao;
	@FXML
	private TableColumn<Bug, Integer> listaDesenvolvedor;
	@FXML
	private TableColumn<Bug, String> listaPrioridade;
	@FXML
	private TableColumn<Bug, String> listaImpacto;
	@FXML
	private TableColumn<Bug, Integer> listaRelease;
	@FXML
	private TableColumn<Bug, Integer> listaIdRequisito;

	private BugController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new BugController();
//			carregarListaBugs();
//			carregarListaPrioridadeImpactoStatus();
//			carregarListaDesenvolvedor();
			// carregarListaRelease();
			// carregarListaComBugs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	private void carregarListaBugs() {
//		List<Bug> listaBugs = controller.enviarListaBug();
//		setTableContent(listaBugs);
//		if (listaBugs.size() > 0) {
//			listaId.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("id"));
//			listaTitulo.setCellValueFactory(new PropertyValueFactory<Bug, String>("titulo"));
//			listaDescricao.setCellValueFactory(new PropertyValueFactory<Bug, String>("descricao"));
//			listaDesenvolvedor.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("idDesenvolvedor"));
//			listaPrioridade.setCellValueFactory(new PropertyValueFactory<Bug, String>("prioridade"));
//			listaImpacto.setCellValueFactory(new PropertyValueFactory<Bug, String>("nivelImpacto"));
//			// listRelease.setCellValueFactory(new PropertyValueFactory<Bug,
//			// Integer>("idRelease"));
//		}
//	}
//
//	private void carregarListaPrioridadeImpactoStatus() {
//		selectPrioridade.getItems().addAll("Alta", "Media", "Baixa");
//		selectImpacto.getItems().addAll("Alta", "Media", "Baixa");
//	}
//
//	private void carregarListaDesenvolvedor() throws IOException {
//		DesenvolvedorController controller = new DesenvolvedorController();
//		List<Desenvolvedor> listDesenvolvedor = controller.enviarListaDesenvolvedor();
//
//		for (Desenvolvedor desenvolvedor : listDesenvolvedor) {
//			selectDesenvolvedor.getItems().addAll(desenvolvedor.getId() + " - " + desenvolvedor.getNome());
//		}
//	}
//
////	private void carregarListaRelease() throws IOException {
////		ReleaseController controller = new ReleaseController();
////		List<Release> listRelease = controller.enviarListaRelease();
////
////		for (Release release : listRelease) {
////			selectRelease.getItems().addAll(release.getId() + " - " + release.getVersao());
////		}
////	}
//
//
//	private void carregarListaComBugs() throws IOException {
//		selectBug.getItems().clear();
//		BugController controller = new BugController();
//		List<Bug> listBug = controller.enviarListaBug();
//
//		for (Bug bug : listBug) {
//			selectBug.getItems().addAll(bug.getId() + " - " + bug.getTitulo());
//		}
//	}
//
//	@FXML
//	private void cadastrarNovoBug(Event e) throws IOException {
//		Bug bug = new Bug();
//		bug.setTitulo(titulo.getText());
//		bug.setDescricao(descricao.getText());
//		bug.setIdDesenvolvedor(Integer.parseInt(selectDesenvolvedor.getSelectionModel().getSelectedItem().toString()
//				.substring(0, selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
//		bug.setPrioridade(selectPrioridade.getSelectionModel().getSelectedItem().toString());
////		bug.setNivelImpacto(selectImpacto.getSelectionModel().getSelectedItem().toString());
////		bug.setIdRelease(Integer.parseInt(selectRelease.getSelectionModel().getSelectedItem().toString().substring(0,
////				selectRelease.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
//		if (!(selectTarefa.getValue() == null))
//			bug.setIdTarefaGeradora(Integer.parseInt(selectTarefa.getSelectionModel().getSelectedItem().toString()
//					.substring(0, selectTarefa.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
//		else
//			bug.setIdBugGerador(Integer.parseInt(selectBug.getSelectionModel().getSelectedItem().toString().substring(0,
//					selectBug.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
//
//		controller.addNewBug(bug);
//		carregarListaBugs();
//
//		carregarListaComBugs();
//	}
//
//	private void setTableContent(List<Bug> listaBugs) {
//		this.listaBugs.getItems().setAll(listaBugs);
//	}

}
