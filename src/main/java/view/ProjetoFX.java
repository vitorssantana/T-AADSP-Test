package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import controller.ProjetoController;
import controller.RequisitoController;
import controller.StakeholderController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Projeto;
import model.Stakeholder;
import utils.AlertController;

public class ProjetoFX implements Initializable {

	private boolean isCadastrarNovoProjeto = true;

	@FXML
	private JFXTextField nome;
	@FXML
	private JFXTextField custo;
	@FXML
	private JFXTextField prazo;

	@FXML
	private JFXComboBox<String> selectStakeholder;
	@FXML
	private TableView<Projeto> listaProjetos;
	@FXML
	private TableColumn<Projeto, Integer> listaId;
	@FXML
	private TableColumn<Projeto, String> listaNome;
	@FXML
	private TableColumn<Projeto, Integer> listaIdStakeholder;
	@FXML
	private TableColumn<Projeto, Double> listaCusto;
	@FXML
	private TableColumn<Projeto, String> listaPrazo;
	@FXML
	private Button btnSalvar;

	private ProjetoController projetoController;

	@Override
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
	public void confirmarProjeto(Event e) {

		Projeto projeto = new Projeto();
		projeto.setNome(nome.getText());
		projeto.setIdStakeholder(Integer.parseInt(selectStakeholder.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectStakeholder.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		projeto.setCusto(Double.valueOf(custo.getText()));
		projeto.setPrazoEntrega(prazo.getText());

		if (isCadastrarNovoProjeto == true) {
			projetoController.cadastrarNovoProjeto(projeto);
			carregarListaProjetos();

			AlertController.alertUsingInformationDialog("Cadastro feito com sucesso!");
		} else {
			projeto.setId(listaProjetos.getSelectionModel().getSelectedItem().getId());
			projetoController.editarProjeto(projeto);
			carregarListaProjetos();
			AlertController.alertUsingInformationDialog("Alteação feita com sucesso!");
		}
		limparCamposTela();
	}

	@FXML
	private void limparCamposTela() {
		// TODO
	}

	private void carregarListaProjetos() {
		List<Projeto> listaProjetos = projetoController.enviarListaProjetos();
		setTableContent(listaProjetos);
		if (listaProjetos.size() > 0) {
			listaId.setCellValueFactory(new PropertyValueFactory<Projeto, Integer>("id"));
			listaNome.setCellValueFactory(new PropertyValueFactory<Projeto, String>("nome"));
			listaIdStakeholder.setCellValueFactory(new PropertyValueFactory<Projeto, Integer>("idStakeholder"));
			listaCusto.setCellValueFactory(new PropertyValueFactory<Projeto, Double>("custo"));
			listaPrazo.setCellValueFactory(new PropertyValueFactory<Projeto, String>("prazoEntrega"));
		}
	}

	private void carregarListaStakeholder() throws IOException {
		StakeholderController controller = new StakeholderController();
		List<Stakeholder> listStakeholder = controller.enviarListaStakeholder();

		for (Stakeholder stakeholder : listStakeholder) {
			selectStakeholder.getItems().addAll(stakeholder.getId() + " - " + stakeholder.getNome());
		}
	}

	@FXML
	private void removerProjeto(Event E) throws IOException {
		if (listaProjetos.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um projeto da lista");
		else {
			if (new RequisitoController().verificarSeProjetoEstaVinculadoARequisito(
					listaProjetos.getSelectionModel().getSelectedItem().getId()) == true) {
				AlertController
						.alertUsingWarningDialog("Não se pode deletar o projeto, ele está vinculado a Requisitos");
			} else {
				// TODO Deletar Projeto
			}
		}
	}

	@FXML
	private void editarProjeto(Event E) {
		if (listaProjetos.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um projeto da lista");
		else {
			nome.clear();
			nome.setText(listaProjetos.getSelectionModel().getSelectedItem().getNome());

			custo.clear();
			custo.setText(String.valueOf(listaProjetos.getSelectionModel().getSelectedItem().getCusto()));

			for (int i = 0; i < selectStakeholder.getItems().size(); i++) {
				if (selectStakeholder.getItems().get(i)
						.contains(listaProjetos.getSelectionModel().getSelectedItem().getIdStakeholder() + " - "))
					selectStakeholder.getSelectionModel().select((selectStakeholder.getItems().get(i)));
			}

			prazo.clear();
			prazo.setText(listaProjetos.getSelectionModel().getSelectedItem().getPrazoEntrega());
			isCadastrarNovoProjeto = false;
		}

	}

	private void setTableContent(List<Projeto> listaProjeto) {
		listaProjetos.getItems().setAll(listaProjeto);
	}

}
