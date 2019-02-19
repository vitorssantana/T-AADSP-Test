package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controller.StakeholderController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Stakeholder;
import utils.AlertController;
import model.Stakeholder;

public class StakeholderFX implements Initializable {

	private boolean isCadastrarNovoStakeholder = true;

	@FXML
	private Button btnSalvar;
	@FXML
	private TextField nome;
	@FXML
	private ComboBox<Integer> selectNotaPrioridade;
	@FXML
	private TableColumn<Stakeholder, Integer> listaId;
	@FXML
	private TableColumn<Stakeholder, String> listaNome;
	@FXML
	private TableColumn<Stakeholder, Integer> listaNotaPrioridade;
	@FXML
	private TableView<Stakeholder> listaStakeholders;

	private StakeholderController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new StakeholderController();
			carregarListaStakeholders();
			carregarListaPrioridade();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void limparCamposTela() {
	}

	@FXML
	public void editarStakeholder() {
		if (listaStakeholders.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um stakeholder da lista");
		else {

			nome.clear();
			nome.setText(listaStakeholders.getSelectionModel().getSelectedItem().getNome());

			for (int i = 0; i < selectNotaPrioridade.getItems().size(); i++) {
				if (selectNotaPrioridade.getItems().get(i)
						.equals(listaStakeholders.getSelectionModel().getSelectedItem().getNotaPrioridade()))
					selectNotaPrioridade.getSelectionModel().select((selectNotaPrioridade.getItems().get(i)));
			}

			isCadastrarNovoStakeholder = false;
		}
	}

	@FXML
	public void confirmarStakeholder(Event e) {

		Stakeholder stakeholder = new Stakeholder();
		stakeholder.setNome(nome.getText());
		stakeholder
				.setNotaPrioridade(Integer.valueOf(selectNotaPrioridade.getSelectionModel().getSelectedItem().toString()));

		if (isCadastrarNovoStakeholder == true) {
			controller.addNewStakeholder(stakeholder);
			carregarListaStakeholders();

			AlertController.alertUsingInformationDialog("Cadastro feito com sucesso!");
		} else {
			stakeholder.setId(listaStakeholders.getSelectionModel().getSelectedItem().getId());
			controller.editarStakeholder(stakeholder);
			carregarListaStakeholders();
			AlertController.alertUsingInformationDialog("Alteação feita com sucesso!");

		}

		limparCamposTela();
	}

	private void carregarListaStakeholders() {
		List<Stakeholder> listaStakeholders = controller.enviarListaStakeholder();
		setTableContent(listaStakeholders);
		if (listaStakeholders.size() > 0) {
			listaId.setCellValueFactory(new PropertyValueFactory<Stakeholder, Integer>("id"));
			listaNome.setCellValueFactory(new PropertyValueFactory<Stakeholder, String>("nome"));
			listaNotaPrioridade.setCellValueFactory(new PropertyValueFactory<Stakeholder, Integer>("notaPrioridade"));
		}
	}

	private void carregarListaPrioridade() {
		selectNotaPrioridade.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	}

	@FXML
	private void cadastrarNovoStakeholder(Event e) {
		Stakeholder stakeholder = new Stakeholder();
		stakeholder.setNome(nome.getText());
		stakeholder
				.setNotaPrioridade(Integer.valueOf(selectNotaPrioridade.getSelectionModel().getSelectedItem().toString()));
		controller.addNewStakeholder(stakeholder);
		carregarListaStakeholders();
	}

	private void setTableContent(List<Stakeholder> listaStakeholder) {
		listaStakeholders.getItems().setAll(listaStakeholder);
	}
}
