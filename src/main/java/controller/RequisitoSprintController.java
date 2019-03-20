package controller;

import java.io.IOException;
import java.util.List;

import db.RequisitoSprintDAO;
import model.RequisitoSprint;

public class RequisitoSprintController {

	private RequisitoSprintDAO dao;

	public RequisitoSprintController() throws IOException {
		dao = new RequisitoSprintDAO();
	}

	public List<RequisitoSprint> retornarListaRequisitoSprint() {
		return dao.retornarListaRequisitoSprint();
	}

	public void addNewRequisitoSprint(RequisitoSprint requisitoSprint) {
		dao.addnewRequisitoSprint(requisitoSprint);
	}

	public void editarRequisitoSprint(RequisitoSprint requisitoSprint) {
		dao.editarDadosRequisitoSprint(requisitoSprint);
	}

	public void removerRequisitoSprint(RequisitoSprint requisitoSprint) {
		dao.removeRequisitoSprint(requisitoSprint);
	}
}
