package controller;

import java.io.IOException;
import java.util.List;

import db.SprintDAO;
import model.Sprint;

public class SprintController {

	private SprintDAO dao;

	public SprintController() throws IOException {
		dao = new SprintDAO();
	}

	public List<Sprint> enviarListaSprint() {
		return dao.retornarListaSprints();
	}

	public void addNewSprint(Sprint sprint) {
		dao.addNewSprint(sprint);
	}

	public void editarSprint(Sprint sprint) {
		dao.editarSprint(sprint);
	}
	
	public void iniciarSprint(Sprint sprint) {
		dao.iniciarSprint(sprint);
	}
	
	public void finalizarSprint(Sprint sprint) {
		dao.finalizarSprint(sprint);
	}
}
