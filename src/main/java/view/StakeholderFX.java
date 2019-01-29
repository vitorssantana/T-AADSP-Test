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

public class StakeholderFX implements Initializable {

	@FXML
	private Button btnSalvar;
	@FXML
	private TextField nome;
	@FXML
	private ComboBox<Integer> selectPrioridade;
	@FXML
	private TableColumn<Stakeholder, Integer> id;
	@FXML
	private TableColumn<Stakeholder, String> nomeList;
	@FXML
	private TableColumn<Stakeholder, Integer> notaPrioridade;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void carregarListaStakeholders() {
		List<Stakeholder> listaStakeholders = controller.enviarListaStakeholder();
		setTableContent(listaStakeholders);
		if (listaStakeholders.size() > 0) {
			id.setCellValueFactory(new PropertyValueFactory<Stakeholder, Integer>("id"));
			nomeList.setCellValueFactory(new PropertyValueFactory<Stakeholder, String>("nome"));
			notaPrioridade.setCellValueFactory(new PropertyValueFactory<Stakeholder, Integer>("notaPrioridade"));
		}
	}

	private void carregarListaPrioridade() {
		selectPrioridade.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9,10);
	}

	@FXML
	private void cadastrarNovoStakeholder(Event e) {
		Stakeholder stakeholder = new Stakeholder();
		stakeholder.setNome(nome.getText());
		stakeholder
				.setNotaPrioridade(Integer.valueOf(selectPrioridade.getSelectionModel().getSelectedItem().toString()));
		controller.addNewStakeholder(stakeholder);
		carregarListaStakeholders();
	}

	private void setTableContent(List<Stakeholder> listaStakeholder) {
		listaStakeholders.getItems().setAll(listaStakeholder);
	}
}
