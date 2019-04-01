package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.PlanoTesteController;
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
import model.PlanoTeste;
import model.Projeto;
import model.Requisito;
import model.Stakeholder;
import utils.AlertController;

public class RequisitoFX implements Initializable {

	private boolean isCadastrarNovoRequisito = true;

	@FXML
	private TextField titulo;
	@FXML
	private ComboBox<Integer> selectNotaPrioridade;
	@FXML
	private ComboBox<String> selectProjeto;
	@FXML
	private ComboBox<String> selectStakeholder;
	@FXML
	private TableView<Requisito> listaRequisitos;
	@FXML
	private TableColumn<Requisito, Integer> listaId;
	@FXML
	private TableColumn<Requisito, String> listaTitulo;
	@FXML
	private TableColumn<Requisito, Integer> listaIdProjeto;
	@FXML
	private TableColumn<Requisito, Integer> listaNotaPrioridade;
	@FXML
	private TableColumn<Requisito, Integer> listaIdStakeholder;
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
			e.printStackTrace();
		}

	}

	@FXML
	private void limparCamposTela() {
		selectProjeto.getSelectionModel().clearSelection();
		selectStakeholder.getSelectionModel().clearSelection();
		selectNotaPrioridade.getSelectionModel().clearSelection();
		titulo.clear();
	}

	public void carregarListaRequisitos() {
		List<Requisito> listaRequisitos = controller.enviarListaRequisitos();
		setTableContent(listaRequisitos);
		if (listaRequisitos.size() > 0) {
			listaId.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("id"));
			listaTitulo.setCellValueFactory(new PropertyValueFactory<Requisito, String>("titulo"));
			listaIdProjeto.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("idProjeto"));
			listaNotaPrioridade.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("notaPrioridade"));
			listaIdStakeholder.setCellValueFactory(new PropertyValueFactory<Requisito, Integer>("idStakeholder"));
		}
	}

	@FXML
	public void confirmarRequisito(Event e) {

		Requisito requisito = new Requisito();
		requisito.setTitulo(titulo.getText());
		requisito.setIdProjeto(Integer.parseInt(selectProjeto.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectProjeto.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		requisito.setNotaPrioridade(
				Integer.valueOf(selectNotaPrioridade.getSelectionModel().getSelectedItem().toString()));
		requisito.setIdStakeholder(Integer.parseInt(selectStakeholder.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectStakeholder.getSelectionModel().getSelectedItem().toString().indexOf(" "))));

		if (isCadastrarNovoRequisito == true) {
			controller.cadastrarNovoRequisito(requisito);
			carregarListaRequisitos();

			AlertController.alertUsingInformationDialog("Cadastro feito com sucesso!");
		} else {
			requisito.setId(listaRequisitos.getSelectionModel().getSelectedItem().getId());
			controller.editarRequisito(requisito);
			carregarListaRequisitos();
			AlertController.alertUsingInformationDialog("Alteação feita com sucesso!");

		}

		limparCamposTela();

	}

	public void carregarListaStakeholders() throws IOException {
		StakeholderController controller = new StakeholderController();
		List<Stakeholder> listStakeholder = controller.enviarListaStakeholder();

		for (Stakeholder stakeholder : listStakeholder) {
			selectStakeholder.getItems().addAll(stakeholder.getId() + " - " + stakeholder.getNome());
		}
	}

	public void carregarListaProjetos() throws IOException {
		ProjetoController controller = new ProjetoController();
		List<Projeto> listProjeto = controller.enviarListaProjetos();

		for (Projeto projeto : listProjeto) {
			selectProjeto.getItems().addAll(projeto.getId() + " - " + projeto.getNome());
		}
	}

	@FXML
	private void editarRequisito(Event E) {
		if (listaRequisitos.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um requisito da lista");
		else {
			titulo.clear();
			titulo.setText(listaRequisitos.getSelectionModel().getSelectedItem().getTitulo());

			for (int i = 0; i < selectStakeholder.getItems().size(); i++) {
				if (selectStakeholder.getItems().get(i)
						.contains(listaRequisitos.getSelectionModel().getSelectedItem().getIdStakeholder() + " - "))
					selectStakeholder.getSelectionModel().select((selectStakeholder.getItems().get(i)));
			}

			for (int i = 0; i < selectProjeto.getItems().size(); i++) {
				if (selectProjeto.getItems().get(i)
						.contains(listaRequisitos.getSelectionModel().getSelectedItem().getIdProjeto() + " - "))
					selectProjeto.getSelectionModel().select((selectProjeto.getItems().get(i)));
			}

			for (int i = 0; i < selectNotaPrioridade.getItems().size(); i++) {
				if (selectNotaPrioridade.getItems().get(i).equals(
						Integer.valueOf(listaRequisitos.getSelectionModel().getSelectedItem().getNotaPrioridade())))
					selectNotaPrioridade.getSelectionModel().select((selectNotaPrioridade.getItems().get(i)));
			}

			isCadastrarNovoRequisito = false;
		}

	}

	@FXML
	private void excluirRequisito() throws IOException {

		if (listaRequisitos.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um requisito da lista");
		else {
			List<PlanoTeste> listaPlanoTestes = new PlanoTesteController().enviarListaPlanoTeste();
			for (int i = 0; i < listaPlanoTestes.size(); i++) {
				if (listaPlanoTestes.get(i).getIdRequisito() == listaRequisitos.getSelectionModel().getSelectedItem().getId()) {
					AlertController.alertUsingWarningDialog("Existe caso de teste vinculado ao requisito.");
					return;
				} else if (i == listaPlanoTestes.size() - 1) {
					controller.removerRequisito(listaRequisitos.getSelectionModel().getSelectedItem());
					AlertController.alertUsingSuccessDialog("Requisito excluido com sucesso!");
					carregarListaRequisitos();
				}
			}
		}

	}

	public void carregarNotaPrioridade() {
		selectNotaPrioridade.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

	private void setTableContent(List<Requisito> listaRequisito) {
		listaRequisitos.getItems().setAll(listaRequisito);
	}

}
