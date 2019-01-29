package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.ProjetoController;
import controller.RequisitoController;
import controller.StakeholderController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Projeto;
import model.Requisito;
import model.Stakeholder;

public class RequisitoFX implements Initializable {

	@FXML
	private ComboBox<Integer> selectNotaPrioridade;
	@FXML
	private ComboBox<String> selectProjeto;
	@FXML
	private ComboBox<String> selectIdStakeholder;
	@FXML
	private TableView<Requisito> listaRequisitos;
	@FXML
	private TextField titulo;
	@FXML
	private TableColumn<Requisito, Integer> listId;
	@FXML
	private TableColumn<Requisito, String> listTitulo;
	@FXML
	private TableColumn<Requisito, Integer> listIdProjeto;
	@FXML
	private TableColumn<Requisito, Integer> listNotaPrioridade;
	@FXML
	private TableColumn<Requisito, Integer> listIdStakeholder;

	private RequisitoController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new RequisitoController();
			carregarListaRequisitos();
			carregarListaProjetos();
			carregarListaStakeholders();
			carregarNotaPrioridade();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void carregarListaRequisitos() {
		List<Requisito> listaRequisitos = controller.enviarListaRequisitos();
		setTableContent(listaRequisitos);
		if (listaRequisitos.size() > 0) {
			listId.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("id"));
			listTitulo.setCellValueFactory(new PropertyValueFactory<Requisito, String>("titulo"));
			listIdProjeto.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("idProjeto"));
			listNotaPrioridade.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("notaPrioridade"));
			listIdStakeholder.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("idStakeholder"));
		}
	}

	public void carregarListaStakeholders() throws IOException {
		StakeholderController controller = new StakeholderController();
		List<Stakeholder> listStakeholder = controller.enviarListaStakeholder();

		for (Stakeholder stakeholder : listStakeholder) {
			selectIdStakeholder.getItems().addAll(stakeholder.getId() + " - " + stakeholder.getNome());
		}
	}

	public void carregarListaProjetos() throws IOException {
		ProjetoController controller = new ProjetoController();
		List<Projeto> listProjeto = controller.enviarListaProjetos();

		for (Projeto projeto : listProjeto) {
			selectProjeto.getItems().addAll(projeto.getId() + " - " + projeto.getNome());
		}
	}

	public void carregarNotaPrioridade() {
		selectNotaPrioridade.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

	@FXML
	public void cadastrarNovoRequisito(Event e) throws IOException {
		Requisito requisito = new Requisito();
		requisito.setTitulo(titulo.getText());
		requisito.setIdProjeto(Integer.parseInt(selectProjeto.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectProjeto.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		requisito.setNotaPrioridade(
				Integer.valueOf(selectNotaPrioridade.getSelectionModel().getSelectedItem().toString()));
		requisito.setIdStakeholder(Integer.parseInt(selectIdStakeholder.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectIdStakeholder.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		controller.cadastrarNovoRequisito(requisito);

		carregarListaRequisitos();
	}

	private void setTableContent(List<Requisito> listaRequisito) {
		listaRequisitos.getItems().setAll(listaRequisito);
	}

}
