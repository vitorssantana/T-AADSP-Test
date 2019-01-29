package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.DesenvolvedorController;
import controller.ReleaseController;
import controller.RequisitoController;
import controller.TarefaController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Desenvolvedor;
import model.Release;
import model.Requisito;
import model.Tarefa;

public class TarefaFX implements Initializable {

	@FXML
	private TextField titulo;

	@FXML
	private ComboBox<String> selectRequisito;
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
	private TableView<Tarefa> listaTarefas;
	@FXML
	private TableColumn<Tarefa, Integer> listId;
	@FXML
	private TableColumn<Tarefa, String> listTitulo;
	@FXML
	private TableColumn<Tarefa, Integer> listIdRequisito;
	@FXML
	private TableColumn<Tarefa, Integer> listDesenvolvedor;
	@FXML
	private TableColumn<Tarefa, String> listPrioridade;
	@FXML
	private TableColumn<Tarefa, String> listImpacto;
	@FXML
	private TableColumn<Tarefa, Integer> listIdRelease;
	@FXML
	private TarefaController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new TarefaController();
			carregarListaTarefas();
			carregarListaPrioridadeImpacto();
			carregarListaDesenvolvedor();
			carregarListaRequisito();
			// carregarListaRelease();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void carregarListaTarefas() {
		List<Tarefa> listaTarefas = controller.enviarListaTarefa();
		setTableContent(listaTarefas);
		if (listaTarefas.size() > 0) {
			listId.setCellValueFactory(new PropertyValueFactory<Tarefa, Integer>("id"));
			listTitulo.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("titulo"));
			listIdRequisito.setCellValueFactory(new PropertyValueFactory<Tarefa, Integer>("idRequisito"));
			listDesenvolvedor.setCellValueFactory(new PropertyValueFactory<Tarefa, Integer>("idDesenvolvedor"));
			listPrioridade.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("prioridade"));
			listImpacto.setCellValueFactory(new PropertyValueFactory<Tarefa, String>("nivelImpacto"));
			listIdRelease.setCellValueFactory(new PropertyValueFactory<Tarefa, Integer>("idRelease"));
		}
	}

	private void carregarListaPrioridadeImpacto() {
		selectPrioridade.getItems().addAll("Alta", "Media", "Baixa");
		selectImpacto.getItems().addAll("Alta", "Media", "Baixa");
	}

	private void carregarListaRequisito() throws IOException {
		List<Requisito> listRequisito = new RequisitoController().enviarListaRequisitos();

		for (Requisito requisito : listRequisito) {
			selectRequisito.getItems().addAll(requisito.getId() + " " + requisito.getTitulo());
		}
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

	@FXML
	private void cadastrarNovaTarefa(Event e) {
		Tarefa tarefa = new Tarefa();
		tarefa.setTitulo(titulo.getText());
		tarefa.setIdRequisito(Integer.parseInt(selectRequisito.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectRequisito.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		tarefa.setIdDesenvolvedor(Integer.parseInt(selectDesenvolvedor.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		tarefa.setPrioridade(selectPrioridade.getSelectionModel().getSelectedItem().toString());
		tarefa.setNivelImpacto(selectImpacto.getSelectionModel().getSelectedItem().toString());
//		tarefa.setIdRelease(Integer.parseInt(selectRelease.getSelectionModel().getSelectedItem().toString().substring(0,
//				selectRelease.getSelectionModel().getSelectedItem().toString().indexOf(" "))));

		controller.addNewTarefa(tarefa);
		carregarListaTarefas();
	}

	private void setTableContent(List<Tarefa> listaTarefas) {
		this.listaTarefas.getItems().setAll(listaTarefas);
	}

}
