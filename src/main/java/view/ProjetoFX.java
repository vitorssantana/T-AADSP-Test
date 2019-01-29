package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controller.ProjetoController;
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
import model.Projeto;
import model.Stakeholder;

public class ProjetoFX implements Initializable {

	@FXML
	private TextField nome;
	@FXML
	private TextField custo;
	@FXML
	private TextField prazoEntrega;

	@FXML
	private ComboBox<String> selectStakeholder;
	@FXML
	private TableView<Projeto> listaProjetos;
	@FXML
	private TableColumn<Projeto, Integer> id;
	@FXML
	private TableColumn<Projeto, String> nomeList;
	@FXML
	private TableColumn<Projeto, Integer> idStakeholder;
	@FXML
	private TableColumn<Projeto, Double> custoList;
	@FXML
	private TableColumn<Projeto, String> prazo;
	@FXML
	private Button btnSalvar;

	private ProjetoController projetoController;

	@Override
	@FXML
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			projetoController = new ProjetoController();
			carregarListaProjetos();
			carregarListaStakeholder();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void cadastrarNovoProjeto(Event e) {
		Projeto projeto = new Projeto();
		projeto.setNome(nome.getText());
		projeto.setIdStakeholder(Integer.parseInt(selectStakeholder.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectStakeholder.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		projeto.setCusto(Double.valueOf(custo.getText()));
		projeto.setPrazoEntrega(prazoEntrega.getText());

		projetoController.cadastrarNovoProjeto(projeto);
		carregarListaProjetos();
	}

	public void carregarListaProjetos() {
		List<Projeto> listaProjetos = projetoController.enviarListaProjetos();
		setTableContent(listaProjetos);
		if (listaProjetos.size() > 0) {
			id.setCellValueFactory(new PropertyValueFactory<Projeto, Integer>("id"));
			nomeList.setCellValueFactory(new PropertyValueFactory<Projeto, String>("nome"));
			idStakeholder.setCellValueFactory(new PropertyValueFactory<Projeto, Integer>("idStakeholder"));
			custoList.setCellValueFactory(new PropertyValueFactory<Projeto, Double>("custo"));
			prazo.setCellValueFactory(new PropertyValueFactory<Projeto, String>("prazoEntrega"));
		}
	}

	public void carregarListaStakeholder() throws IOException {
		StakeholderController controller = new StakeholderController();
		List<Stakeholder> listStakeholder = controller.enviarListaStakeholder();

		for (Stakeholder stakeholder : listStakeholder) {
			selectStakeholder.getItems().addAll(stakeholder.getId() + " - " + stakeholder.getNome());
		}
	}

	private void setTableContent(List<Projeto> listaProjeto) {
		listaProjetos.getItems().setAll(listaProjeto);
	}

}
