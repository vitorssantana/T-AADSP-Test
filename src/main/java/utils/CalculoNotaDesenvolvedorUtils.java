package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.BugController;
import controller.DesenvolvedorController;
import controller.DesenvolvedorController;
import controller.RequisitoSprintController;
import model.Bug;
import model.Desenvolvedor;
import model.Desenvolvedor;
import model.RequisitoSprint;
import model.Sprint;

public class CalculoNotaDesenvolvedorUtils {

	private List<RequisitoSprint> listaRequisitoSprint;
	private DesenvolvedorController controller;

	public CalculoNotaDesenvolvedorUtils(Sprint sprint) throws IOException {
		controller = new DesenvolvedorController();
		listaRequisitoSprint = new ArrayList<RequisitoSprint>();
		List<RequisitoSprint> listaRequisitoSprint = new RequisitoSprintController().retornarListaRequisitoSprint();

		for (RequisitoSprint requisitoSprint : listaRequisitoSprint) {
			if (requisitoSprint.getIdSprint() == sprint.getId()) {
				this.listaRequisitoSprint.add(requisitoSprint);
			}
		}
	}

	public void calcularNotaDesenvolvedor() throws IOException {
		List<Desenvolvedor> listaDesenvolvedor = controller.enviarListaDesenvolvedor();

		int contador = 0;
		List<Bug> listaBugs = new BugController().enviarListaBug();
		List<Bug> listaBugsDaSprint = new ArrayList<Bug>();

		for (Bug bug : listaBugs) {
			for (RequisitoSprint requisitoSprint : listaRequisitoSprint) {
				if (bug.getIdRequisitoSprint() == requisitoSprint.getId()) {
					listaBugsDaSprint.add(bug);
				}
			}
		}

		if (listaBugsDaSprint.size() == 0) {
			return;
		} else {
			for (Desenvolvedor desenvolvedor : listaDesenvolvedor) {
				for (int j = 0; j < listaBugsDaSprint.size(); j++) {
					if (desenvolvedor.getId() == listaBugsDaSprint.get(j).getIdDesenvolvedor()) {
						desenvolvedor.setNota(Math.round((desenvolvedor.getNota() - 0.3) * 100) / 100d);
						contador++;
					}
					if (j == listaBugsDaSprint.size() - 1 && contador != 0) {
						controller = new DesenvolvedorController();
						controller.editarDesenvolvedor(desenvolvedor);
					}

					else if (j == listaBugsDaSprint.size() - 1 && contador == 0) {
						double nota = Double.valueOf(desenvolvedor.getNota());
						nota = Math.round((desenvolvedor.getNota() + 0.5) * 100) / 100d;
						desenvolvedor.setNota(desenvolvedor.getNota());
						controller.editarDesenvolvedor(desenvolvedor);
					}
				}
			}
		}

	}

}
