package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controller.ReleaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Release;

public class ReleaseFX implements Initializable {

	private ReleaseController controller;
	private static ReleaseFX INSTANCE;
	private Release release;
	private static Stage STAGE;
	
	@FXML
	private TableView<Release> listaRelease;
	@FXML
	private TableColumn<Release, Integer> listId;
	@FXML
	private TableColumn<Release, String> listVersao;
	@FXML
	private TableColumn<Release, String> listDataEntrega;
	@FXML
	private TableColumn<Release, Void> listOpcoes;
	@FXML
	private TableColumn<Release, Void> listExecucaoTeste;
	@FXML
	private TextField versao, dataEntrega;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new ReleaseController();
			carregarListaRelease();
			carregarOpcaoTarefaBug();
			carregarOpcaoExecucaoTestes();
			INSTANCE = this;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ReleaseFX getInstanCe() {
		return INSTANCE;
	}

	private void carregarListaRelease() {

		List<Release> listaReleases = controller.enviarListaRelease();
		setTableContent(listaReleases);
		if (listaReleases.size() > 0) {
			listId.setCellValueFactory(new PropertyValueFactory<Release, Integer>("id"));
			listVersao.setCellValueFactory(new PropertyValueFactory<Release, String>("versao"));
			listDataEntrega.setCellValueFactory(new PropertyValueFactory<Release, String>("dataEntrega"));
		}
	}

	@FXML
	public void cadastrarNovaRelease() {
		Release release = new Release();
		release.setVersao(versao.getText());
		release.setDataEntrega(dataEntrega.getText());

		controller.addNewRelease(release);
		carregarListaRelease();
	}

	private void carregarOpcaoTarefaBug() {

		Callback<TableColumn<Release, Void>, TableCell<Release, Void>> cellFactory = new Callback<TableColumn<Release, Void>, TableCell<Release, Void>>() {
			@Override
			public TableCell<Release, Void> call(final TableColumn<Release, Void> listOpcoes) {
				final TableCell<Release, Void> cell = new TableCell<Release, Void>() {

					private final Button btn = new Button("Vincular Tarefa ou Bug");

					{
						btn.setOnAction((ActionEvent event) -> {
							release = getTableView().getItems().get(getIndex());

							try {
								FXMLLoader fxmlLoader = new FXMLLoader();
								fxmlLoader.setLocation(getClass().getResource("vincularBugTarefaRelease.fxml"));
								Scene scene;
								scene = new Scene(fxmlLoader.load());
								Stage stage = new Stage();
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		listOpcoes.setCellFactory(cellFactory);

	}
	
	private void carregarOpcaoExecucaoTestes() {

		Callback<TableColumn<Release, Void>, TableCell<Release, Void>> cellFactory = new Callback<TableColumn<Release, Void>, TableCell<Release, Void>>() {
			@Override
			public TableCell<Release, Void> call(final TableColumn<Release, Void> listOpcoes) {
				final TableCell<Release, Void> cell = new TableCell<Release, Void>() {

					private final Button btn = new Button("Registrar Resultados dos Testes");

					{
						btn.setOnAction((ActionEvent event) -> {
							release = getTableView().getItems().get(getIndex());

							try {
								FXMLLoader fxmlLoader = new FXMLLoader();
								fxmlLoader.setLocation(getClass().getResource("indicarStatusTeste.fxml"));
								Scene scene;
								scene = new Scene(fxmlLoader.load());
								Stage stage = new Stage();
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {
								e.printStackTrace();
							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		listExecucaoTeste.setCellFactory(cellFactory);

	}
	
	


	private void setTableContent(List<Release> listaRelease) {
		this.listaRelease.getItems().setAll(listaRelease);
	}

	public Release getRelease() {
		return release;
	}

	public static Stage getStageInstance() {
		return STAGE;
	}

}
