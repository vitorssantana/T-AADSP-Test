package view;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.PredicaoTesteController;
import controller.ProjetoController;
import controller.RequisitoController;
import controller.RequisitoSprintController;
import controller.SprintController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PredicaoTeste;
import model.Projeto;
import model.Requisito;
import model.RequisitoSprint;
import model.PredicaoTeste;
import model.Sprint;
import utils.AlertController;

public class PredicaoTesteFX implements Initializable {

	@FXML
	private TableView<PredicaoTeste> listaPredicaoTestes;
	@FXML
	private TableColumn<PredicaoTeste, String> listaNomeRequisito, listaNomeProjeto;
	@FXML
	private TableColumn<PredicaoTeste, Double> listaPrioridadeAlta, listaPrioridadeMedia, listaPrioridadeBaixa;
	@FXML
	private Button btnRealizarPredicao;
	@FXML
	private TextField numTestadoresDisponiveis;

	private PredicaoTesteController controller;
	private Sprint sprintAtual;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO verificar se existe sprint aberta
		// TODO se tiver sprint aberta, identificar se ja foi gerada a predicao
		// TODO se ja foi gerada predicao, nao deixe gerar mais (a predição para essa
		// sprint já foi feita)

		try {
			int size = 0;
			controller = new PredicaoTesteController();
			List<Sprint> listaSprint = new SprintController().enviarListaSprint();
			List<PredicaoTeste> listaPredicaoTeste = controller.retornarListaPredicao();
			for (Sprint sprint : listaSprint) {
				size++;
				if (sprint.getStatus().equals("Em Andamento")) {
					sprintAtual = sprint;
					for (int i = 0; i < listaPredicaoTeste.size(); i++) {
						if (listaPredicaoTeste.get(i).getIdSprint() == sprint.getId()) {
							carregarListaPredicaoTeste();
							btnRealizarPredicao.setDisable(true);
						} else if (null == null) {
							// TODO ver se te algo a se fazer nesse caso
							btnRealizarPredicao.setDisable(false);
						}
					}

				} else if (size == listaSprint.size() - 1) {
					// TODO emitir mensagem dizendo que nao existe sprint em andamento
					AlertController.alertUsingWarningDialog("Não existe sprint em andamento");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarListaPredicaoTeste() {
		List<PredicaoTeste> listaPredicaoTeste = controller.retornarListaPredicao();
		setTableContent(listaPredicaoTeste);

		if (listaPredicaoTeste.size() > 0) {
			listaNomeRequisito.setCellValueFactory(new PropertyValueFactory<PredicaoTeste, String>("nomeRequisito"));
			listaNomeProjeto.setCellValueFactory(new PropertyValueFactory<PredicaoTeste, String>("nomeProjeto"));
			listaPrioridadeAlta
					.setCellValueFactory(new PropertyValueFactory<PredicaoTeste, Double>("probabilidadeAlta"));
			listaPrioridadeMedia
					.setCellValueFactory(new PropertyValueFactory<PredicaoTeste, Double>("probabilidadeMedia"));
			listaPrioridadeBaixa
					.setCellValueFactory(new PropertyValueFactory<PredicaoTeste, Double>("probabilidadeBaixa"));
		}
	}

	private void setTableContent(List<PredicaoTeste> listaPredicaoTeste) {
		this.listaPredicaoTestes.getItems().setAll(listaPredicaoTeste);
	}

	@FXML
	public void realizarPredicao(Event e) throws IOException, ParseException {
		List<RequisitoSprint> listaTodosRequisitoSprint = new RequisitoSprintController()
				.retornarListaRequisitoSprint();
		List<Requisito> listaRequisito = new RequisitoController().enviarListaRequisitos();
		List<Requisito> listaRequisitosDaSprint = new ArrayList<Requisito>();

		for (RequisitoSprint requisitoSprint : listaTodosRequisitoSprint) {
			if (requisitoSprint.getIdSprint() == sprintAtual.getId()) {
				for (Requisito requisito : listaRequisito) {
					if (requisito.getId() == requisitoSprint.getIdRequisito()) {
						listaRequisitosDaSprint.add(requisito);
					}
				}
			}
		}

		for (Requisito requisito : listaRequisitosDaSprint) {
			List<Projeto> listaProjeto = new ProjetoController().enviarListaProjetos();
			PredicaoTeste predicaoTeste = new PredicaoTeste();

			for (Projeto projeto : listaProjeto) {
				if (projeto.getId() == requisito.getIdProjeto()) {
					predicaoTeste.setIdProjeto(projeto.getId());
				}
			}

			predicaoTeste.setIdRequisito(requisito.getId());
			predicaoTeste.setIdSprint(sprintAtual.getId());
			controller.realizarPredicao(predicaoTeste, requisito, sprintAtual);
		}

		btnRealizarPredicao.setDisable(true);
		carregarListaPredicaoTeste();
		AlertController.alertUsingSuccessDialog("Predição feita com sucesso!");
	}
}
