package view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.DesenvolvedorController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Desenvolvedor;

public class DesenvolvedorFX implements Initializable {

	@FXML
	private Button btnSalvar;
	@FXML
	private TextField nome;
	@FXML
	private ComboBox<String> selectNivel;
	@FXML
	private TableColumn<Desenvolvedor, Integer> listId;
	@FXML
	private TableColumn<Desenvolvedor, String> listNome;
	@FXML
	private TableColumn<Desenvolvedor, Integer> listNivel;
	@FXML
	private TableView<Desenvolvedor> listaDesenvolvedores;
	private DesenvolvedorController controller;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			controller = new DesenvolvedorController();
			carregarListaDesenvolvedors();
			carregarListaPrioridade();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void carregarListaDesenvolvedors() {
		List<Desenvolvedor> listaDesenvolvedores = controller.enviarListaDesenvolvedor();
		setTableContent(listaDesenvolvedores);
		if (listaDesenvolvedores.size() > 0) {
			listId.setCellValueFactory(new PropertyValueFactory<Desenvolvedor, Integer>("id"));
			listNome.setCellValueFactory(new PropertyValueFactory<Desenvolvedor, String>("nome"));
			listNivel.setCellValueFactory(new PropertyValueFactory<Desenvolvedor, Integer>("nivel"));
		}
	}

	private void carregarListaPrioridade() {
		selectNivel.getItems().addAll("Junior","Pleno","Senior");
	}

	@FXML
	private void cadastrarNovoDesenvolvedor(Event e) {
		Desenvolvedor desenvolvedor = new Desenvolvedor();
		desenvolvedor.setNome(nome.getText());
		desenvolvedor.setNivel(selectNivel.getSelectionModel().getSelectedItem().toString());
		controller.addNewDesenvolvedor(desenvolvedor);
		carregarListaDesenvolvedors();
	}

	private void setTableContent(List<Desenvolvedor> listaDesenvolvedor) {
		listaDesenvolvedores.getItems().setAll(listaDesenvolvedor);
	}
}
