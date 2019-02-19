package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import controller.PlanoTesteController;
import controller.RequisitoController;
import controller.RequisitoController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PlanoTeste;
import model.PlanoTeste;
import model.Requisito;
import utils.AlertController;
import model.PlanoTeste;

public class PlanoTesteFX implements Initializable {

	private boolean isCadastrarNovoTeste = true;

	@FXML
	private TableView<PlanoTeste> listaTestes;
	@FXML
	private TableColumn<PlanoTeste, Integer> listaId;
	@FXML
	private TableColumn<PlanoTeste, String> listaDescricao;
	@FXML
	private TableColumn<PlanoTeste, String> listaTipoTeste;
	@FXML
	private TextField descricao;
	@FXML
	private JFXComboBox<String> selectRequisito, selectTipoTeste;
	@FXML
	private Button btnEditar, btnConfirmar, btnRemover;

	private PlanoTesteController controller;
	private int requisitoSelecionado;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new PlanoTesteController();
			carregarListaRequisito();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void carregarListaElementosRestantes(Event e) {
		requisitoSelecionado = Integer.parseInt(selectRequisito.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectRequisito.getSelectionModel().getSelectedItem().toString().indexOf(" ")));
		btnEditar.setDisable(false);
		btnRemover.setDisable(false);
		btnConfirmar.setDisable(false);
		listaTestes.setDisable(false);
		selectTipoTeste.setDisable(false);
		selectRequisito.setDisable(false);
		descricao.setDisable(false);

		carregarListaPlanoTestes();
		carregarListaTipoTeste();
	}

	public void carregarListaTipoTeste() {
		selectTipoTeste.getItems().add("Teste de Configuração");
		selectTipoTeste.getItems().add("Teste de Instalação");
		selectTipoTeste.getItems().add("Teste de Integridade");
		selectTipoTeste.getItems().add("Teste de Segurança");
		selectTipoTeste.getItems().add("Teste Funcional");
		selectTipoTeste.getItems().add("Teste de Unidade");
		selectTipoTeste.getItems().add("Teste de Integração");
		selectTipoTeste.getItems().add("Teste de Volume");
		selectTipoTeste.getItems().add("Teste de Performance");
		selectTipoTeste.getItems().add("Teste de Usabilidade");
		selectTipoTeste.getItems().add("Teste de Caixa Branca");
		selectTipoTeste.getItems().add("Teste Caixa Preta");
		selectTipoTeste.getItems().add("Teste de Regressão");
		selectTipoTeste.getItems().add("Teste de Manutenção");
	}

	public void carregarListaRequisito() throws IOException {
		RequisitoController controller = new RequisitoController();
		List<Requisito> listRequisito = controller.enviarListaRequisitos();

		for (Requisito requisito : listRequisito) {
			selectRequisito.getItems().addAll(requisito.getId() + " - " + requisito.getTitulo());
		}
	}

	@FXML
	public void confirmarPlanoTeste(Event e) {
		PlanoTeste planoTeste = new PlanoTeste();
		planoTeste.setDescricao(descricao.getText());
		planoTeste.setTipoTeste(selectTipoTeste.getSelectionModel().getSelectedItem());
		planoTeste.setIdRequisito(Integer.parseInt(selectRequisito.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectRequisito.getSelectionModel().getSelectedItem().toString().indexOf(" "))));

		if (isCadastrarNovoTeste == true) {
			controller.addNewPlanoTeste(planoTeste);
			carregarListaPlanoTestes();

			AlertController.alertUsingInformationDialog("Cadastro feito com sucesso!");
		} else {
			planoTeste.setId(listaTestes.getSelectionModel().getSelectedItem().getId());
			controller.editarTeste(planoTeste);
			carregarListaPlanoTestes();
			AlertController.alertUsingInformationDialog("Alteração feita com sucesso!");
		}
		carregarListaPlanoTestes();
		limparCamposTela();
	}

	@FXML
	private void editarTeste(Event E) {
		if (listaTestes.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um teste da lista");
		else {

			descricao.clear();
			descricao.setText(listaTestes.getSelectionModel().getSelectedItem().getDescricao());

			for (int i = 0; i < selectTipoTeste.getItems().size(); i++) {
				if (selectTipoTeste.getItems().get(i)
						.contains(listaTestes.getSelectionModel().getSelectedItem().getTipoTeste()))
					selectTipoTeste.getSelectionModel().select((selectTipoTeste.getItems().get(i)));
			}

			isCadastrarNovoTeste = false;
		}

	}

	@FXML
	public void limparCamposTela() {
	}

	public void carregarListaPlanoTestes() {
		List<PlanoTeste> listaPlanoTestes = controller.enviarListaPlanoTeste();
		List<PlanoTeste> listaPlanoTestesPorRequisito = new ArrayList<PlanoTeste>();
		for (PlanoTeste planoTeste : listaPlanoTestes) {
			if (requisitoSelecionado == planoTeste.getIdRequisito())
				listaPlanoTestesPorRequisito.add(planoTeste);
		}
		setTableContent(listaPlanoTestesPorRequisito);
		if (listaPlanoTestesPorRequisito.size() > 0) {
			listaId.setCellValueFactory(new PropertyValueFactory<PlanoTeste, Integer>("id"));
			listaDescricao.setCellValueFactory(new PropertyValueFactory<PlanoTeste, String>("descricao"));
			listaTipoTeste.setCellValueFactory(new PropertyValueFactory<PlanoTeste, String>("tipoTeste"));
		}
	}

	private void setTableContent(List<PlanoTeste> listaPlanoTeste) {
		listaTestes.getItems().setAll(listaPlanoTeste);
	}

}
