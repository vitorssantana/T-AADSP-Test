package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import controller.DesenvolvedorController;
import controller.DesenvolvedorRequisitoSprintController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Desenvolvedor;
import model.DesenvolvedorRequisitoSprint;
import utils.AlertController;

public class DesenvolvedorRequisitoSprintFX implements Initializable {

	@FXML
	private ComboBox<String> selectDesenvolvedor;
	@FXML
	private TextField nivelParticipacao;
	@FXML
	private TableView<DesenvolvedorRequisitoSprint> listaDesenvolvedorRequisitoSprint;
	@FXML
	private TableColumn<DesenvolvedorRequisitoSprint, String> listaDesenvolvedor;
	@FXML
	private TableColumn<DesenvolvedorRequisitoSprint, String> listaNivelParticipacao;
	List<DesenvolvedorRequisitoSprint> listaDesenvolvedoresRequisitoSprint;

	private static DesenvolvedorRequisitoSprintFX INSTANCE;

	private DesenvolvedorRequisitoSprintController controller;

	public static DesenvolvedorRequisitoSprintFX getInstance() {
		return INSTANCE;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			carregarListaDesenvolvedor();
			controller = new DesenvolvedorRequisitoSprintController();
			carregarListaDesenvolvedorRequisitoSprint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void carregarListaDesenvolvedorRequisitoSprint() {
		listaDesenvolvedoresRequisitoSprint = controller.retornarListaDesenvolvedorRequisitoSprint();
		List<DesenvolvedorRequisitoSprint> listaRemovertDRS = new ArrayList<DesenvolvedorRequisitoSprint>();

		for (DesenvolvedorRequisitoSprint drs : listaDesenvolvedoresRequisitoSprint) {
			if (drs.getIdRequisitoSprint() != RequisitoSprintFX.getInstance().getRequisitoSprintSelecionada().getId()) {
				listaRemovertDRS.add(drs);
			}
		}
		listaDesenvolvedoresRequisitoSprint.removeAll(listaRemovertDRS);

		setTableContent(listaDesenvolvedoresRequisitoSprint);
		if (listaDesenvolvedoresRequisitoSprint.size() > 0) {
			listaDesenvolvedor.setCellValueFactory(
					new PropertyValueFactory<DesenvolvedorRequisitoSprint, String>("idDesenvolvedor"));
			listaNivelParticipacao.setCellValueFactory(
					new PropertyValueFactory<DesenvolvedorRequisitoSprint, String>("nivelParticipacao"));
		}
	}

	private void setTableContent(List<DesenvolvedorRequisitoSprint> listaDesenvolvedorRequisitoSprint) {
		this.listaDesenvolvedorRequisitoSprint.getItems().setAll(listaDesenvolvedorRequisitoSprint);
	}

	@FXML
	public void remover() {

	}

	@FXML
	public void voltar() throws IOException {
		DashboardFX.getInstance().acessarTelaVincularRequisitoSprint();
	}

	@FXML
	public void confirmar() {
		int totalPorcentagem = 0;

		if (selectDesenvolvedor.getSelectionModel().getSelectedItem() == null
				|| nivelParticipacao.getText().isBlank()) {
			AlertController.alertUsingWarningDialog("Preencha todos os campos");
			return;
		} else {
			for (int i = 0; i < listaDesenvolvedoresRequisitoSprint.size(); i++)
				totalPorcentagem = totalPorcentagem + listaDesenvolvedoresRequisitoSprint.get(i).getNivelParticipacao();

			if (totalPorcentagem == 100) {
				AlertController.alertUsingWarningDialog("A soma da porcentagem de participação já chegou 100%");
				return;
			}

			else if (Integer.valueOf(nivelParticipacao.getText()) + totalPorcentagem > 100) {
				AlertController.alertUsingWarningDialog("O valor informado ultrapassa 100%");
				return;
			}
		}

		Iterator<DesenvolvedorRequisitoSprint> iterator = listaDesenvolvedorRequisitoSprint.getItems().iterator();

		while (iterator.hasNext()) {
			DesenvolvedorRequisitoSprint drs = iterator.next();
			if (drs.getIdDesenvolvedor() == Integer
					.parseInt(selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().substring(0,
							selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().indexOf(" ")))) {
				AlertController.alertUsingWarningDialog("O desenvolvedor já foi vinculado");
				return;
			}
		}

		DesenvolvedorRequisitoSprint drs = new DesenvolvedorRequisitoSprint();
		drs.setIdDesenvolvedor(Integer.parseInt(selectDesenvolvedor.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		drs.setIdRequisitoSprint(RequisitoSprintFX.getInstance().getRequisitoSprintSelecionada().getId());
		drs.setNivelParticipacao(Integer.valueOf(nivelParticipacao.getText()));
		controller.addNewDesenvolvedorRequisitoSprint(drs);
		AlertController.alertUsingSuccessDialog("Desenvolvedor vinculado com sucesso!");
		carregarListaDesenvolvedorRequisitoSprint();
	}

	public boolean existeDesenvolvedorNaLista() {
		return true;
	}

	public void carregarListaDesenvolvedor() throws IOException {
		List<Desenvolvedor> listaDesenvolvedores = new DesenvolvedorController().enviarListaDesenvolvedor();

		for (Desenvolvedor desenvolvedor : listaDesenvolvedores) {
			selectDesenvolvedor.getItems().add(desenvolvedor.getId() + " - " + desenvolvedor.getNome());
		}
	}

}
