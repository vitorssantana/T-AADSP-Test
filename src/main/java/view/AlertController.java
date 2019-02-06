package view;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AlertController {


	public static void alertUsingInformationDialog(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informação Importante");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	public static void alertUsingWarningDialog(String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Atenção");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	public static void alertUsingErrorDialog(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erro");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	public static void alertUsingSuccessDialog(String text) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sucesso");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}

}
