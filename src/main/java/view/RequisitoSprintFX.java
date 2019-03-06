package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.RequisitoController;
import controller.RequisitoSprintController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Requisito;
import model.RequisitoSprint;
import utils.AlertController;

public class RequisitoSprintFX implements Initializable {

	@FXML
	private TableView<RequisitoSprint> listaRequisitoSprint;
	@FXML
	private TableColumn<RequisitoSprint, String> listaRequisito;
	@FXML
	private TableColumn<RequisitoSprint, String> listaNivelImpacto;
	@FXML
	private ComboBox<String> selectRequisito, selectImpactoAlteracoes;
	@FXML
	private Button btnRemover, btnEditar, btnVincularDesenvolvedor, btnConfirmar;

	private RequisitoSprintController controller;
	private boolean isVincularNovoRequisitoSprint = true;
	private RequisitoSprint requisitoSprintSelecionada;
	private static RequisitoSprintFX INSTANCE;

	public static RequisitoSprintFX getInstance() {
		return INSTANCE;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			INSTANCE = this;
			controller = new RequisitoSprintController();
			carregarListaRequisitoSprint();
			carregarListaRequisito();
			carregarOpcoesNivelImpacto();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarListaRequisitoSprint() {
		List<RequisitoSprint> listaRequisitoSprint = controller.retornarListaRequisitoSprint();

		for (int i = 0; i < listaRequisitoSprint.size(); i++) {
			if (listaRequisitoSprint.get(i).getIdSprint() != SprintFX.getInstance().getSprintSelecionada().getId())
				listaRequisitoSprint.remove(i);
		}

		setTableContent(listaRequisitoSprint);
		if (listaRequisitoSprint.size() > 0) {
			listaRequisito.setCellValueFactory(new PropertyValueFactory<RequisitoSprint, String>("idRequisito"));
			listaNivelImpacto
					.setCellValueFactory(new PropertyValueFactory<RequisitoSprint, String>("nivelImpactoAlteracoes"));
		}
	}

	public void carregarListaRequisito() throws IOException {
		RequisitoController controller = new RequisitoController();
		List<Requisito> listRequisito = controller.enviarListaRequisitos();

		for (Requisito requisito : listRequisito) {
			selectRequisito.getItems().addAll(requisito.getId() + " - " + requisito.getTitulo());
		}
	}

	public void carregarOpcoesNivelImpacto() {
		selectImpactoAlteracoes.getItems().addAll("Baixo", "Médio", "Alto");
	}

	@FXML
	public void ativarCamposRestantes() {
		selectImpactoAlteracoes.setDisable(false);
		
		listaRequisitoSprint.setDisable(false);
		btnConfirmar.setDisable(false);

	}

	public void limparCamposTela() {
		selectRequisito.getSelectionModel().clearSelection();
		selectImpactoAlteracoes.getSelectionModel().clearSelection();
		selectRequisito.setDisable(false);
		selectImpactoAlteracoes.setDisable(true);
		btnConfirmar.setDisable(true);
		btnRemover.setDisable(true);
		btnEditar.setDisable(true);
		btnVincularDesenvolvedor.setDisable(true);

	}

	@FXML
	public void selecionarRequisitoSprint() {

		btnVincularDesenvolvedor.setDisable(false);
		btnRemover.setDisable(false);
		btnConfirmar.setDisable(true);
		btnEditar.setDisable(false);
		selectRequisito.setDisable(true);
		selectImpactoAlteracoes.setDisable(true);
		requisitoSprintSelecionada = listaRequisitoSprint.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void editar() {
		carregarOpcoesNivelImpacto();
		if (listaRequisitoSprint.getSelectionModel().getSelectedItem() == null) {
			AlertController.alertUsingWarningDialog("Selecione um desenvolvedor da lista");
		}

		else {
			selectRequisito.setDisable(true);
			selectImpactoAlteracoes.setDisable(true);

			selectImpactoAlteracoes.getSelectionModel()
					.select(listaRequisitoSprint.getSelectionModel().getSelectedItem().getNivelImpactoAlteracoes());

			for (int i = 0; i < selectRequisito.getItems().size(); i++) {
				if (selectRequisito.getItems().get(i)
						.contains(listaRequisitoSprint.getSelectionModel().getSelectedItem().getIdRequisito() + " - "))
					selectRequisito.getSelectionModel().select((selectRequisito.getItems().get(i)));
			}

			isVincularNovoRequisitoSprint = false;
		}
	}

	@FXML
	public void confirmar() {

		RequisitoSprint requisitoSprint = new RequisitoSprint();
		requisitoSprint.setIdRequisito(Integer.parseInt(selectRequisito.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectRequisito.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		requisitoSprint.setIdSprint(SprintFX.getInstance().getSprintSelecionada().getId());
		requisitoSprint.setNivelImpactoAlteracoes(selectImpactoAlteracoes.getSelectionModel().getSelectedItem());

		if (isVincularNovoRequisitoSprint) {

			for (int i = 0; i < listaRequisitoSprint.getItems().size(); i++) {
				if (listaRequisitoSprint.getItems().get(i).getIdRequisito().equals(requisitoSprint.getIdRequisito())) {
					AlertController.alertUsingWarningDialog("O requisito já foi adicionado");
					return;
				}
			}

			requisitoSprint.setVinculouDesenvolvedor(false);
			controller.addNewRequisitoSprint(requisitoSprint);
			carregarListaRequisitoSprint();
			AlertController.alertUsingSuccessDialog("Requisito vinculado com sucesso!");

		} else {
			requisitoSprint.setId(listaRequisitoSprint.getSelectionModel().getSelectedItem().getId());
			controller.editarRequisitoSprint(requisitoSprint);
			carregarListaRequisitoSprint();
			AlertController.alertUsingSuccessDialog("Alterado com sucesso!");
		}

		isVincularNovoRequisitoSprint = true;
		limparCamposTela();
	}

	public RequisitoSprint getRequisitoSprintSelecionada() {
		return requisitoSprintSelecionada;
	}

	@FXML
	public void vincularDesenvolvedor() throws IOException {
		DashboardFX.getInstance().acessarTelaVincularDesenvolvedorRequisitoNaSprint();
	}

	private void setTableContent(List<RequisitoSprint> listaDesenvolvedor) {
		listaRequisitoSprint.getItems().setAll(listaDesenvolvedor);
	}
}
