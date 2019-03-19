package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import controller.SprintController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Sprint;
import utils.AlertController;
import utils.CalculoNotaDesenvolvedorUtils;

public class SprintFX implements Initializable {

	private boolean isCadastrarNovaSprint = true;

	private SprintController controller;
	private static SprintFX INSTANCE;
	private Sprint sprint;
	private static Stage STAGE;

	@FXML
	private TableView<Sprint> listaSprints;
	@FXML
	private TableColumn<Sprint, Integer> listaId;
	@FXML
	private TableColumn<Sprint, String> listaNome;
	@FXML
	private TableColumn<Sprint, String> listaDataInicio;
	@FXML
	private TableColumn<Sprint, String> listaDataFim;
	@FXML
	private TableColumn<Sprint, String> listaStatus;
	@FXML
	private TextField nome, dataInicio, dataFim;

	private Sprint sprintSelecionada;

	public static SprintFX getInstance() {
		return INSTANCE;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new SprintController();
			carregarListaSprints();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void carregarListaSprints() {
		List<Sprint> listaSprints = controller.enviarListaSprint();
		setTableContent(listaSprints);
		if (listaSprints.size() > 0) {
			listaId.setCellValueFactory(new PropertyValueFactory<Sprint, Integer>("id"));
			listaNome.setCellValueFactory(new PropertyValueFactory<Sprint, String>("nome"));
			listaDataInicio.setCellValueFactory(new PropertyValueFactory<Sprint, String>("dataInicio"));
			listaDataFim.setCellValueFactory(new PropertyValueFactory<Sprint, String>("dataFim"));
			listaStatus.setCellValueFactory(new PropertyValueFactory<Sprint, String>("status"));
		}
	}

	@FXML
	public void cadastrarNovaSprint() {

		controller.addNewSprint(sprint);
		carregarListaSprints();
	}

	private void setTableContent(List<Sprint> listaSprint) {
		this.listaSprints.getItems().setAll(listaSprint);
	}

	@FXML
	public void editarSprint() {
		if (listaSprints.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione uma sprint da lista");
		else {
			nome.clear();
			nome.setText(listaSprints.getSelectionModel().getSelectedItem().getNome());

			dataInicio.clear();
			dataInicio.setText(String.valueOf(listaSprints.getSelectionModel().getSelectedItem().getDataInicio()));

			dataFim.clear();
			dataFim.setText(String.valueOf(listaSprints.getSelectionModel().getSelectedItem().getDataFim()));

			isCadastrarNovaSprint = false;
		}
	}

	@FXML
	public void confirmarSprint() {
		Sprint sprint = new Sprint();
		sprint.setNome(nome.getText());
		sprint.setDataInicio(dataInicio.getText());
		sprint.setDataFim(dataFim.getText());

		if (isCadastrarNovaSprint == true) {
			sprint.setStatus("Pendente");
			controller.addNewSprint(sprint);
			carregarListaSprints();

			AlertController.alertUsingInformationDialog("Cadastro feito com sucesso!");
		} else {
			sprint.setId(listaSprints.getSelectionModel().getSelectedItem().getId());
			controller.editarSprint(sprint);
			carregarListaSprints();
			AlertController.alertUsingInformationDialog("Alteação feita com sucesso!");
		}

		limparCamposTela();

	}

	@FXML
	public void limparCamposTela() {
		nome.clear();
		dataInicio.clear();
		dataFim.clear();
	}

	@FXML
	public void exibirPopUpOpcoesSprint() throws IOException {

		if (listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Finalizada")
				|| listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Pendente")) {
			AlertController.alertUsingInformationDialog("Essa sprint não foi iniciada ou já foi finalizada");
			return;
		} else if (listaSprints.getSelectionModel().getSelectedItem() == null) {
			AlertController.alertUsingWarningDialog("Selecione uma sprint da lista");
		} else {
			INSTANCE = this;
			sprintSelecionada = listaSprints.getSelectionModel().getSelectedItem();
			if (listaSprints.getSelectionModel().getSelectedItem() == null)
				AlertController.alertUsingWarningDialog("Selecione uma sprint da lista");
			else {

				Alert dialogoExe = new Alert(Alert.AlertType.INFORMATION);
				ButtonType btnInserirSprint = new ButtonType("Inserir Release");
				ButtonType btnIndicarStatusTestes = new ButtonType("Indicar Status dos Testes");

				dialogoExe.setTitle("Opções de Sprnit");
				dialogoExe.setHeaderText("Selecione uma das opções");
				dialogoExe.setContentText("O que você deseja?");
				dialogoExe.getButtonTypes().setAll(btnInserirSprint, btnIndicarStatusTestes);

				dialogoExe.setHeaderText(null);

				Optional<ButtonType> result = dialogoExe.showAndWait();

				if (result.get() == btnInserirSprint) {
					DashboardFX.getInstance().acessarTelaVincularRequisitoSprint();

				} else if (result.get() == btnIndicarStatusTestes) {
					DashboardFX.getInstance().acessarTelaIndicarStatusCasosTeste();

				}

			}
		}
	}

	@FXML
	public void iniciarSprint() {
		List<Sprint> listaSprints = controller.enviarListaSprint();
		for (Sprint sprint : listaSprints) {
			if (sprint.getStatus().endsWith("Em Andamento")) {
				AlertController.alertUsingWarningDialog("Já existe sprint em andamento");
				return;
			}
		}

		if (this.listaSprints.getSelectionModel().getSelectedItem() == null) {
			AlertController.alertUsingWarningDialog("Selecione uma sprint da lista");
		} else if (this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Finalizada")) {
			AlertController.alertUsingWarningDialog("A Sprint não pode ser iniciada, pois já foi finalizada");
		} else if (this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Em Andamento")) {
			AlertController.alertUsingWarningDialog("A Sprint não pode ser iniciada, pois já está em andamento");
		} else {
			controller.iniciarSprint(this.listaSprints.getSelectionModel().getSelectedItem());
			carregarListaSprints();
			AlertController.alertUsingSuccessDialog("Sprint Iniciada com sucesso!");
		}
	}

	@FXML
	public void finalizarSprint() throws IOException, InterruptedException {
		if (this.listaSprints.getSelectionModel().getSelectedItem() == null) {
			AlertController.alertUsingWarningDialog("Selecione uma sprint da lista");
		} else if (this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Finalizada")) {
			AlertController.alertUsingWarningDialog("A Sprint  já foi finalizada");
		} else if (this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Pendente")) {
			AlertController.alertUsingWarningDialog("A Sprint não está em andamento");
		} else if (this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Em Andamento")) {
			CalculoNotaDesenvolvedorUtils utils = new CalculoNotaDesenvolvedorUtils(
					this.listaSprints.getSelectionModel().getSelectedItem());
			utils.calcularNotaDesenvolvedor();
			controller = new SprintController();
			controller.finalizarSprint(this.listaSprints.getSelectionModel().getSelectedItem());
			AlertController.alertUsingSuccessDialog("Sprint Finalizada com sucesso!");
			carregarListaSprints();
		}
	}

	@FXML
	public void removerSprint() {
		if (this.listaSprints.getSelectionModel().getSelectedItem() == null) {
			AlertController.alertUsingWarningDialog("Selecione uma sprint da lista");
		} else if (this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Em Andamento")) {
			AlertController.alertUsingWarningDialog("Nao e possivel remover uma Sprint en Andamento");
		} else if(this.listaSprints.getSelectionModel().getSelectedItem().getStatus().equals("Finalizada")){
			AlertController.alertUsingWarningDialog("Nao e possivel remover uma Sprint que já foi finalizada");
		} 
		else{
			controller.removerSprint(this.listaSprints.getSelectionModel().getSelectedItem());
			AlertController.alertUsingSuccessDialog("Sprint removida com sucesso!");
			carregarListaSprints();
		}
	}

	public Sprint getSprintSelecionada() {
		return sprintSelecionada;
	}

}
