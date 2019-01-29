package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import controller.BugController;
import controller.TarefaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Bug;
import model.Tarefa;

public class VincularBugTarefaReleaseFX implements Initializable {

	@FXML
	private ListView<Tarefa> listTarefasDisponiveis, listTarefasJaAdicionadas;
	@FXML
	private ListView<Bug> listBugsJaAdicionados, listBugsDisponiveis;
	@FXML
	private Button adicionarTarefa, removerTarefa, adicionarBug, removerBug, btnSalvarAlteracao;

	private BugController bugController;
	private TarefaController tarefaController;
	private ReleaseFX releaseFX;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			releaseFX = ReleaseFX.getInstanCe();
			bugController = new BugController();
			tarefaController = new TarefaController();
			carregarBugsDisponiveis();
			carregarBugsJaVinculados();
			carregarTarefasDisponiveis();
			carregarTarefasJaCadastradas();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void carregarBugsDisponiveis() {
		List<Bug> listaBugsDisponiveis = bugController.retornarListaBugsDisponiveis();
		ObservableList<Bug> wordsList = FXCollections.observableArrayList();
		for (Bug bug : listaBugsDisponiveis) {
			wordsList.add(bug);
		}
		listBugsDisponiveis.setCellFactory(new Callback<ListView<Bug>, ListCell<Bug>>() {

			@Override
			public ListCell<Bug> call(ListView<Bug> param) {
				ListCell<Bug> cell = new ListCell<Bug>() {

					@Override
					protected void updateItem(Bug item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.toString());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
		});

		listBugsDisponiveis.setItems(wordsList);
	}

	public void carregarTarefasDisponiveis() {
		List<Tarefa> listaTarefasDisponiveis = tarefaController.retornarListaTarefasDisponiveis();
		ObservableList<Tarefa> wordsList = FXCollections.observableArrayList();
		for (Tarefa tarefa : listaTarefasDisponiveis) {
			wordsList.add(tarefa);
		}
		listTarefasDisponiveis.setCellFactory(new Callback<ListView<Tarefa>, ListCell<Tarefa>>() {

			@Override
			public ListCell<Tarefa> call(ListView<Tarefa> param) {
				ListCell<Tarefa> cell = new ListCell<Tarefa>() {

					@Override
					protected void updateItem(Tarefa item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.toString());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
		});

		listTarefasDisponiveis.setItems(wordsList);
	}

	public void carregarBugsJaVinculados() {
		List<Bug> listaBugsJaVinculados = bugController.retornarListaBugsVinculados(releaseFX.getRelease().getId());
		ObservableList<Bug> wordsList = FXCollections.observableArrayList();
		for (Bug bug : listaBugsJaVinculados) {
			wordsList.add(bug);
		}
		listBugsJaAdicionados.setCellFactory(new Callback<ListView<Bug>, ListCell<Bug>>() {

			@Override
			public ListCell<Bug> call(ListView<Bug> param) {
				ListCell<Bug> cell = new ListCell<Bug>() {

					@Override
					protected void updateItem(Bug item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.toString());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
		});

		listBugsJaAdicionados.setItems(wordsList);
	}

	public void carregarTarefasJaCadastradas() {
		List<Tarefa> listaTarefasDisponiveis = tarefaController
				.retornarListaTarefasVinculados(releaseFX.getRelease().getId());
		ObservableList<Tarefa> wordsList = FXCollections.observableArrayList();
		for (Tarefa tarefa : listaTarefasDisponiveis) {
			wordsList.add(tarefa);
		}
		listTarefasJaAdicionadas.setCellFactory(new Callback<ListView<Tarefa>, ListCell<Tarefa>>() {

			@Override
			public ListCell<Tarefa> call(ListView<Tarefa> param) {
				ListCell<Tarefa> cell = new ListCell<Tarefa>() {

					@Override
					protected void updateItem(Tarefa item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							setText(item.toString());
						} else {
							setText("");
						}
					}
				};
				return cell;
			}
		});

		listTarefasJaAdicionadas.setItems(wordsList);
	}

	@FXML
	private void adicionarTarefa(Event e) {
		if (listTarefasDisponiveis.getItems().size() > 0) {
			listTarefasJaAdicionadas.getItems().add(listTarefasDisponiveis.getSelectionModel().getSelectedItem());
			listTarefasDisponiveis.getItems().remove(listTarefasDisponiveis.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void removerTarefa(Event e) {
		if (listTarefasJaAdicionadas.getItems().size() > 0) {
			listTarefasDisponiveis.getItems().add(listTarefasJaAdicionadas.getSelectionModel().getSelectedItem());
			listTarefasJaAdicionadas.getItems().remove(listTarefasJaAdicionadas.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void adicionarBug(Event e) {
		if (listBugsDisponiveis.getItems().size() > 0) {
			listBugsJaAdicionados.getItems().add(listBugsDisponiveis.getSelectionModel().getSelectedItem());
			listBugsDisponiveis.getItems().remove(listBugsDisponiveis.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void removerBug(Event e) {
		if (listBugsJaAdicionados.getItems().size() > 0) {
			listBugsDisponiveis.getItems().add(listBugsJaAdicionados.getSelectionModel().getSelectedItem());
			listBugsJaAdicionados.getItems().remove(listBugsJaAdicionados.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void salvarAlteracao(ActionEvent e) throws InterruptedException, IOException {
		salvarAlteracaoTarefaRelease();
		bugController = new BugController();
		salvarAlteracaoBugRelease();
	}

	private void salvarAlteracaoBugRelease() throws InterruptedException {
		List<Bug> listaBugsAdicionados = new ArrayList<Bug>();
		for (int i = 0; i < listBugsJaAdicionados.getItems().size(); i++)
			listaBugsAdicionados.add(listBugsJaAdicionados.getItems().get(i));
		bugController.vincularBugARelease(listaBugsAdicionados, releaseFX.getRelease().getId());

		List<Bug> listaBugsJaCadastrados = new ArrayList<Bug>();
		for (int i = 0; i < listBugsDisponiveis.getItems().size(); i++)
			listaBugsJaCadastrados.add(listBugsDisponiveis.getItems().get(i));
		bugController.desvincularBugARelease(listaBugsJaCadastrados);
	}

	private void salvarAlteracaoTarefaRelease() throws InterruptedException {
		List<Tarefa> listaTarefasAdicionadas = new ArrayList<Tarefa>();
		for (int i = 0; i < listTarefasJaAdicionadas.getItems().size(); i++)
			listaTarefasAdicionadas.add(listTarefasJaAdicionadas.getItems().get(i));
		// TODO verificar se listaTarefasAdicionadas está vazia
		tarefaController.vincularTarefaARelease(listaTarefasAdicionadas, releaseFX.getRelease().getId());

		List<Tarefa> listaTarefasJaCadastradas = new ArrayList<Tarefa>();
		for (int i = 0; i < listTarefasDisponiveis.getItems().size(); i++)
			listaTarefasJaCadastradas.add(listTarefasDisponiveis.getItems().get(i));
		// TODO verificar se listaTarefasCadastradas está vazia
		tarefaController.desvincularTarefaARelease(listaTarefasJaCadastradas);
	}

}
