package view;

import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.DesenvolvedorController;
import controller.RequisitoController;
import controller.RequisitoSprintController;
import controller.SprintController;
import controller.BugController;
import controller.BugController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Desenvolvedor;
import model.Bug;
import model.Requisito;
import model.RequisitoSprint;
import model.Sprint;
import utils.AlertController;
import model.Bug;
import model.Bug;

public class BugFX implements Initializable {

	@FXML
	private TextField titulo;
	@FXML
	private TextArea descricao;
	@FXML
	private ComboBox<String> selectDesenvolvedor, selectNivelImpacto, selectRequisito;
	@FXML
	private Button btnConfirmar, btnRemover, btnEditar;
	@FXML
	private TableView<Bug> listaBugs;
	@FXML
	private TableColumn<Bug, Integer> listaId;
	@FXML
	private TableColumn<Bug, String> listaTitulo, listaDescricao;
	@FXML
	private TableColumn<Bug, Integer> listaIdDesenvolvedor;
	@FXML
	private TableColumn<Bug, String> listaNivelImpacto;

	private int idRequisitoSprintAtual;

	private BugController controller;

	private boolean isCadastrarNovoBug = true;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new BugController();
			carregarListaRequisitos();
			carregarListaDesenvolvedor();
			carregarNivelImpacto();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void carregarListaRequisitos() throws IOException {
		List<Sprint> listaSprints = new SprintController().enviarListaSprint();
		List<RequisitoSprint> listaRequisitoSprint = new RequisitoSprintController().retornarListaRequisitoSprint();
		List<Integer> listaIdRequisitos = new ArrayList<Integer>();
		List<Requisito> listaRequisito = new RequisitoController().enviarListaRequisitos();

		for (int i = 0; i < listaSprints.size(); i++) {
			if (listaSprints.get(i).getStatus().equals("Em Andamento")) {
				for (RequisitoSprint requisitoSprint : listaRequisitoSprint) {
					if (requisitoSprint.getIdSprint().equals(listaSprints.get(i).getId())) {
						idRequisitoSprintAtual = requisitoSprint.getId();
						listaIdRequisitos.add(requisitoSprint.getIdRequisito());
					}
				}
				for (Requisito requisito : listaRequisito) {
					if (listaIdRequisitos.contains(requisito.getId())) {
						selectRequisito.getItems().add(requisito.getId() + " - " + requisito.getTitulo());
					}
				}
			} else if (i == listaSprints.size() - 1) {
				AlertController.alertUsingErrorDialog("Não existe Sprint em andamento");
				selectRequisito.setDisable(true);
			}
		}
	}

	@FXML
	public void desbloquearCamposRestantes() {
		btnConfirmar.setDisable(false);
		btnRemover.setDisable(false);
		btnEditar.setDisable(false);
		titulo.setDisable(false);
		selectDesenvolvedor.setDisable(false);
		selectNivelImpacto.setDisable(false);
		descricao.setDisable(false);
		listaBugs.setDisable(false);
		carregarListaBugs();
	}

	private void carregarListaBugs() {
		List<Bug> listaBugs = controller.enviarListaBug();
		setTableContent(listaBugs);
		if (listaBugs.size() > 0) {
			listaId.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("id"));
			listaTitulo.setCellValueFactory(new PropertyValueFactory<Bug, String>("titulo"));
			listaDescricao.setCellValueFactory(new PropertyValueFactory<Bug, String>("descricao"));
			listaIdDesenvolvedor.setCellValueFactory(new PropertyValueFactory<Bug, Integer>("idDesenvolvedor"));
			listaNivelImpacto.setCellValueFactory(new PropertyValueFactory<Bug, String>("nivelImpacto"));
		}
	}

	private void carregarListaDesenvolvedor() throws IOException {
		DesenvolvedorController controller = new DesenvolvedorController();
		List<Desenvolvedor> listDesenvolvedor = controller.enviarListaDesenvolvedor();

		for (Desenvolvedor desenvolvedor : listDesenvolvedor) {
			selectDesenvolvedor.getItems().addAll(desenvolvedor.getId() + " - " + desenvolvedor.getNome());
		}
	}

	private void setTableContent(List<Bug> listaBugs) {
		this.listaBugs.getItems().setAll(listaBugs);
	}

	@FXML
	public void editar() {
		if (listaBugs.getSelectionModel().getSelectedItem() == null)
			AlertController.alertUsingWarningDialog("Selecione um bug da lista");
		else {

			titulo.clear();
			titulo.setText(listaBugs.getSelectionModel().getSelectedItem().getTitulo());
			descricao.setText(listaBugs.getSelectionModel().getSelectedItem().getDescricao());

			for (int i = 0; i < selectDesenvolvedor.getItems().size(); i++) {
				if (selectDesenvolvedor.getItems().get(i)
						.contains(listaBugs.getSelectionModel().getSelectedItem().getIdDesenvolvedor() + " - "))
					selectDesenvolvedor.getSelectionModel().select((selectDesenvolvedor.getItems().get(i)));
			}

			for (int i = 0; i < selectNivelImpacto.getItems().size(); i++) {
				if (selectNivelImpacto.getItems().get(i).equals(listaBugs.getSelectionModel().getSelectedItem().getNivelImpacto()))
					selectNivelImpacto.getSelectionModel().select((selectNivelImpacto.getItems().get(i)));
			}

			isCadastrarNovoBug = false;
		}
	}
	
	public void carregarNivelImpacto() {
		selectNivelImpacto.getItems().addAll("Baixo", "Médio", "Alto");
	}

	@FXML
	public void confirmar() {

		Bug bug = new Bug();
		bug.setDescricao(descricao.getText());
		bug.setTitulo(titulo.getText());
		bug.setNivelImpacto(selectNivelImpacto.getSelectionModel().getSelectedItem());
		bug.setIdRequisitoSprint(idRequisitoSprintAtual);
		bug.setIdDesenvolvedor(Integer.parseInt(selectDesenvolvedor.getSelectionModel().getSelectedItem().toString()
				.substring(0, selectDesenvolvedor.getSelectionModel().getSelectedItem().toString().indexOf(" "))));
		
		if (isCadastrarNovoBug == true) {
			controller.addNewBug(bug);
			carregarListaBugs();

			AlertController.alertUsingInformationDialog("Cadastro feito com sucesso!");
		} else {
			bug.setId(listaBugs.getSelectionModel().getSelectedItem().getId());
			controller.editarBug(bug);
			carregarListaBugs();
			AlertController.alertUsingInformationDialog("Alteração feita com sucesso!");
		}
		isCadastrarNovoBug = true;
		carregarListaBugs();
		
	}

}
