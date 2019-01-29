package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.BugController;
import controller.TarefaController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Bug;
import model.Release;
import model.Tarefa;

public class IndicarStatusTesteFX implements Initializable {
	@FXML
	private TableView<?> requisitoStatusList;
	@FXML
	private TableColumn<?, ?> listDescricao;
	@FXML
	private TableColumn<?, ?> listStatusTeste;
	@FXML
	private ComboBox<?> selectRequisito;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			carregarListaRequisitos();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void carregarListaRequisitos() throws IOException {
		Release release = ReleaseFX.getInstanCe().getRelease();
		List<Integer> listaRequisitos;
		// Release -> Tarefa/Bug -> Requisitos

		List<Tarefa> listaTarefa = new TarefaController().retornarListaTarefasVinculados(release.getId());

		for (Tarefa tarefa : listaTarefa) {
			if (verificarSeidRequsitoJaEstaNaLista(listaTarefa, tarefa.getIdRequisito()) == false) {

			}

		}
	}

	@FXML
	public void carregarListaTestes(Event e) {

	}

	@FXML
	public void salvarStatusTeste(Event e) {

	}

	public boolean verificarSeidRequsitoJaEstaNaLista(List<Tarefa> listaTarefa, int idRequisito) {
		for (Tarefa tarefa : listaTarefa) {
			if (tarefa.getIdRequisito() == idRequisito)
				return true;
		}
		return false;
	}

}