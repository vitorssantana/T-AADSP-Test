package view;

import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.DesenvolvedorController;
import controller.ReleaseController;
import controller.BugController;
import controller.TarefaController;
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
import model.Release;
import model.Bug;
import model.Tarefa;
import model.Bug;

public class BugFX implements Initializable {

	@FXML
	private TextField titulo;
	@FXML
	private TextArea descricao;
	@FXML
	private ComboBox<String> selectDesenvolvedor;
	@FXML
	private ComboBox<String> selectPrioridade;
	@FXML
	private ComboBox<String> selectImpacto;
//	@FXML
//	private ComboBox<String> selectRelease;
	@FXML
	private ComboBox<String> selectTarefa;
	@FXML
	private ComboBox<String> selectBug;
	@FXML
	private Button btnSalvar;
	@FXML
	private TableView<Bug> listaBugs;
	@FXML
	private TableColumn<Bug, Integer> listId;
	@FXML
	private TableColumn<Bug, String> listTitulo, listDescricao;
	@FXML
	private TableColumn<Bug, Integer> listDesenvolvedor;
	@FXML
	private TableColumn<Bug, String> listPrioridade;
	@FXML
	private TableColumn<Bug, String> listImpacto;
	@FXML
	private TableColumn<Bug, Integer> listRelease;
	@FXML
	private TableColumn<Bug, Integer> listTarefaGeradora;
	@FXML
	private TableColumn<Bug, Integer> listBugGerador;
	@FXML
	private BugController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new BugController();
			carregarListaBugs();
			carregarListaPrioridadeImpactoStatus();
			carregarListaDesenvolvedor();
			//carregarListaRelease();
			carregarListaComBugs();
			carregarListaTarefa();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void carregarListaBugs() {
		List<Bug> listaBugs = controller.enviarListaBug();
		setTableContent(listaBugs);
		if (listaBugs.size() > 0) {
			listId.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("id"));
			listTitulo.setCellValueFactory(new PropertyValueFactory<Bug, String>("titulo"));
			listDescricao.setCellValueFactory(new PropertyValueFactory<Bug, String>("descricao"));
			listDesenvolvedor.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("idDesenvolvedor"));
			listPrioridade.setCellValueFactory(new PropertyValueFactory<Bug, String>("prioridade"));
			listImpacto.setCellValueFactory(new PropertyValueFactory<Bug, String>("nivelImpacto"));
			//listRelease.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("idRelease"));
			listTarefaGeradora.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("idTarefaGeradora"));
			listBugGerador.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("idBugGerador"));
		}
	}

	private void carregarListaPrioridadeImpactoStatus() {
		selectPrioridade.getItems().addAll("Alta", "Media", "Baixa");
		selectImpacto.getItems().addAll("Alta", "Media", "Baixa");
	}

	private void carregarListaDesenvolvedor() throws IOException {
		DesenvolvedorController controller = new DesenvolvedorController();
		List<Desenvolvedor> listDesenvolvedor = controller.enviarListaDesenvolvedor();

		for (Desenvolvedor desenvolvedor : listDesenvolvedor) {
			selectDesenvolvedor.getItems().addAll(desenvolvedor.getId() + " - " + desenvolvedor.getNome());
		}
	}

//	private void carregarListaRelease() throws IOException {
//		ReleaseController controller = new ReleaseController();
//		List<Release> listRelease = controller.enviarListaRelease();
//
//		for (Release release : listRelease) {
//			selectRelease.getItems().addAll(release.getId() + " - " + release.getVersao());
//		}
//	}

	private void carregarListaTarefa() throws IOException {
		TarefaController controller = new TarefaController();
		List<Tarefa> listTarefa = controller.enviarListaTarefa();

		for (Tarefa tarefa : listTarefa) {
			selectTarefa.getItems().addAll(tarefa.getId() + " - " + tarefa.getTitulo());
		}
	}

	private void carregarListaComBugs() throws IOException {
		selectBug.getItems().clear();
		BugController controller = new BugController();
		List<Bug> listBug = controller.enviarListaBug();

		for (Bug bug : listBug) {
			selectBug.getItems().addAll(bug.getId() + " - " + bug.getTitulo());
		}
	}

	@FXML
	private void cadastrarNovoBug(Event e) throws IOException {
		Bug bug = new Bug();
		bug.setTitulo(titulo.getText());
		bug.setDescricao(descricao.getText());
		bug.setIdDesenvolvedor(Integer.parseInt(selectDesenvolvedor.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		bug.setPrioridade(selectPrioridade.getSelectionModel().getSelectedItem().toString());
//		bug.setNivelImpacto(selectImpacto.getSelectionModel().getSelectedItem().toString());
//		bug.setIdRelease(Integer.parseInt(selectRelease.getSelectionModel().getSelectedItem().toString().substring(0,
//				selectRelease.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		if (!(selectTarefa.getValue() == null))
			bug.setIdTarefaGeradora(Integer.parseInt(selectTarefa.getSelectionModel().getSelectedItem().toString()
					.substring(0, selectTarefa.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		else
			bug.setIdBugGerador(Integer.parseInt(selectBug.getSelectionModel().getSelectedItem().toString().substring(0,
					selectBug.getSelectionModel().getSelectedItem().toString().indexOf(" "))));

		controller.addNewBug(bug);
		carregarListaBugs();

		carregarListaComBugs();
	}

	@FXML
	private void selecionarBugOriginario(Event e) {
		String selecao = selectBug.getSelectionModel().getSelectedItem();
		if (!selectTarefa.getSelectionModel().isEmpty())
			selectTarefa.getSelectionModel().clearSelection();
		selectBug.getSelectionModel().select(selecao);
	}

	@FXML
	private void selecionarTarefaOriginaria(Event e) {
		String selecao = selectTarefa.getSelectionModel().getSelectedItem();
		if (!selectBug.getSelectionModel().isEmpty())
			selectBug.getSelectionModel().clearSelection();
		selectTarefa.getSelectionModel().select(selecao);
	}

	private void setTableContent(List<Bug> listaBugs) {
		this.listaBugs.getItems().setAll(listaBugs);
	}

}
