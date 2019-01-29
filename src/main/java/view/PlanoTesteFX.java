package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.PlanoTesteController;
import controller.RequisitoController;
import controller.RequisitoController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PlanoTeste;
import model.PlanoTeste;
import model.Requisito;
import model.PlanoTeste;

public class PlanoTesteFX implements Initializable {

	@FXML
	private TableView<PlanoTeste> listPlanoTestes;
	@FXML
	private TableColumn<PlanoTeste, Integer> listId;
	@FXML
	private TableColumn<PlanoTeste, String> listDescricao;
	@FXML
	private TableColumn<PlanoTeste, Integer> listIdRequisito;
	@FXML
	private TableColumn<PlanoTeste, String> listTipoTeste;
	@FXML
	private TextArea descricao;
	@FXML
	private ComboBox<String> selectRequisito, selectTipoTeste;

	private PlanoTesteController controller;
	private RequisitoController requisitoController;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new PlanoTesteController();
			carregarListaTipoTeste();
			carregarListaRequisito();
			carregarListaPlanoTestes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void cadastrarNovoPlanoTeste(Event e) {
		PlanoTeste planoTeste = new PlanoTeste();
		planoTeste.setDescricao(descricao.getText());
		planoTeste.setTipoTeste(selectTipoTeste.getSelectionModel().getSelectedItem());
		planoTeste.setIdRequisito(Integer.parseInt(selectRequisito.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectRequisito.getSelectionModel().getSelectedItem().toString().indexOf(" "))));

		controller.addNewPlanoTeste(planoTeste);
		carregarListaPlanoTestes();
	}

	public void carregarListaPlanoTestes() {
		List<PlanoTeste> listaPlanoTestes = controller.enviarListaPlanoTeste();
		setTableContent(listaPlanoTestes);
		if (listaPlanoTestes.size() > 0) {
			listId.setCellValueFactory(new PropertyValueFactory<PlanoTeste, Integer>("id"));
			listDescricao.setCellValueFactory(new PropertyValueFactory<PlanoTeste, String>("descricao"));
			listTipoTeste.setCellValueFactory(new PropertyValueFactory<PlanoTeste, String>("tipoTeste"));
			listIdRequisito.setCellValueFactory(new PropertyValueFactory<PlanoTeste, Integer>("idRequisito"));
		}
	}

	private void setTableContent(List<PlanoTeste> listaPlanoTeste) {
		listPlanoTestes.getItems().setAll(listaPlanoTeste);
	}

}
