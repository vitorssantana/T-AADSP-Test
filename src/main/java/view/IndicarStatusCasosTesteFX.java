package view;

import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.PlanoTesteController;
import controller.RequisitoController;
import controller.RequisitoSprintController;
import controller.StatusCasosTesteController;
import db.PlanoTesteDAO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PlanoTeste;
import model.Requisito;
import model.RequisitoSprint;
import model.Sprint;
import model.StatusCasosTeste;
import utils.AlertController;

public class IndicarStatusCasosTesteFX implements Initializable {
	@FXML
	private ComboBox<String> selectRequisito, selectStatusTeste;
	@FXML
	private TableView<StatusCasosTeste> listaStatusCasosTeste;
	@FXML
	private TableColumn<StatusCasosTeste, String> listaDescricao, listaStatus;
	@FXML
	private Button btnConfirmar;

	private StatusCasosTesteController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			controller = new StatusCasosTesteController();
			carregarListaRequisitos();
			carregarListaStatus();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarListaRequisitos() throws IOException {
		RequisitoController requisitoController = new RequisitoController();
		RequisitoSprintController requisitoSprintController = new RequisitoSprintController();

		List<Requisito> listaRequisitos = requisitoController.enviarListaRequisitos();
		List<Requisito> listaRequisitosVinculados = new ArrayList<Requisito>();
		List<RequisitoSprint> listaRequisitoSprint = requisitoSprintController.retornarListaRequisitoSprint();
		Sprint sprint = SprintFX.getInstance().getSprintSelecionada();

		for (int i = 0; i < listaRequisitoSprint.size(); i++) {
			if (sprint.getId() == listaRequisitoSprint.get(i).getIdSprint()) {

				for (int j = 0; j < listaRequisitos.size(); j++) {
					if (listaRequisitos.get(j).getId() == listaRequisitoSprint.get(i).getIdRequisito()) {
						listaRequisitosVinculados.add(listaRequisitos.get(j));
					}
				}
			}
		}

		for (Requisito requisito : listaRequisitosVinculados) {
			selectRequisito.getItems().addAll(requisito.getId() + " - " + requisito.getTitulo());
		}

	}

	@FXML
	public void carregarCamposRestantes() throws IOException {
		listaStatusCasosTeste.setDisable(false);
		carregarListaStatusCasosTeste();
	}

	@FXML
	public void carregarListaStatusCasosTeste() throws IOException {

		RequisitoSprint requisitoSprintAtual = null;
		int idRequisitoAtual = Integer.parseInt(selectRequisito.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectRequisito.getSelectionModel().getSelectedItem().toString().indexOf(" ")));

		List<RequisitoSprint> listaRequisitoSprint = new RequisitoSprintController().retornarListaRequisitoSprint();
		List<PlanoTeste> listaPlanosTeste = new PlanoTesteController().enviarListaPlanoTeste();

		for (RequisitoSprint requisitoSprint : listaRequisitoSprint) {
			if (requisitoSprint.getIdRequisito() == idRequisitoAtual
					&& requisitoSprint.getIdSprint() == SprintFX.getInstance().getSprintSelecionada().getId()) {
				requisitoSprintAtual = requisitoSprint;
				break;
			}
		}

		List<StatusCasosTeste> listaStatusCasosTeste = controller.retornarListaStatusCasosTeste();

		if (listaStatusCasosTeste.size() == 0) {
			for (PlanoTeste planoTeste : listaPlanosTeste) {
				if (planoTeste.getIdRequisito().equals(requisitoSprintAtual.getIdRequisito())) {
					gravarStatusCasosTeste(planoTeste, requisitoSprintAtual);
				}
			}
		} else {
			listaStatusCasosTeste = controller.retornarListaStatusCasosTeste();

			for (int i = 0; i < listaStatusCasosTeste.size(); i++) {
				if (listaStatusCasosTeste.get(i).getIdRequisitoSprint() != requisitoSprintAtual.getId()
						&& i == listaStatusCasosTeste.size() - 1) {
					for (PlanoTeste planoTeste : listaPlanosTeste) {
						if (planoTeste.getIdRequisito().equals(requisitoSprintAtual.getIdRequisito())) {
							gravarStatusCasosTeste(planoTeste, requisitoSprintAtual);
						}
					}
				} else if (listaStatusCasosTeste.get(i).getIdRequisitoSprint() == requisitoSprintAtual.getId()) {
					break;
				}
			}
		}

		List<StatusCasosTeste> listaStatusCasoTesteSelecionados = new ArrayList<StatusCasosTeste>();
		listaStatusCasosTeste = controller.retornarListaStatusCasosTeste();

		for (StatusCasosTeste casosTeste : listaStatusCasosTeste) {
			if (casosTeste.getIdRequisitoSprint() == requisitoSprintAtual.getId()) {
				listaStatusCasoTesteSelecionados.add(casosTeste);
			}
		}

		for (PlanoTeste planoTeste : listaPlanosTeste) {
			for (StatusCasosTeste statusCasosTeste : listaStatusCasoTesteSelecionados) {
				if (planoTeste.getId() == statusCasosTeste.getIdCasoTeste())
					statusCasosTeste.setDescricao(planoTeste.getDescricao());
			}
		}

		setTableContent(listaStatusCasoTesteSelecionados);
		if (listaStatusCasoTesteSelecionados.size() > 0) {
			listaDescricao.setCellValueFactory(new PropertyValueFactory<StatusCasosTeste, String>("descricao"));
			listaStatus.setCellValueFactory(new PropertyValueFactory<StatusCasosTeste, String>("status"));
		}
	}

	private void setTableContent(List<StatusCasosTeste> listaStatusCasoTeste) {
		this.listaStatusCasosTeste.getItems().setAll(listaStatusCasoTeste);
	}

	public void gravarStatusCasosTeste(PlanoTeste planoTeste, RequisitoSprint requisitoSprintAtual) {
		StatusCasosTeste casosTeste = new StatusCasosTeste();
		casosTeste.setIdCasoTeste(planoTeste.getId());
		casosTeste.setIdRequisitoSprint(requisitoSprintAtual.getId());
		casosTeste.setStatus(" ");

		controller.addStatusCasosTeste(casosTeste);
	}

	@FXML
	public void selecionarCasoTeste(Event e) {
		btnConfirmar.setDisable(false);
		selectStatusTeste.setDisable(false);
	}

	@FXML
	public void confirmar() throws IOException {
		listaStatusCasosTeste.getSelectionModel().getSelectedItem().setStatus(selectStatusTeste.getSelectionModel().getSelectedItem());

		controller.editarStatusCasosTeste(listaStatusCasosTeste.getSelectionModel().getSelectedItem());

		AlertController.alertUsingSuccessDialog("O status do caso de teste foi indicado com sucesso!");
		carregarListaStatusCasosTeste();
		
		listaStatusCasosTeste.getSelectionModel().clearSelection();
		
		btnConfirmar.setDisable(true);
	}

	@FXML
	public void carregarListaStatus() {
		selectStatusTeste.getItems().addAll("Sucesso", "Falha", "N/A");
	}

	@FXML
	public void voltar() throws IOException {
		DashboardFX.getInstance().acessarTelaSprints();
	}

}
