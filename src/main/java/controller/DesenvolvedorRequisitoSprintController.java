package controller;

import java.io.IOException;
import java.util.List;

import db.DesenvolvedorRequisitoSprintDAO;
import model.DesenvolvedorRequisitoSprint;

public class DesenvolvedorRequisitoSprintController {

	private DesenvolvedorRequisitoSprintDAO dao;

	public DesenvolvedorRequisitoSprintController() throws IOException {
		dao = new DesenvolvedorRequisitoSprintDAO();
	}

	public List<DesenvolvedorRequisitoSprint> retornarListaDesenvolvedorRequisitoSprint() {
		return dao.retornarListaDesenvolvedorRequisitoSprint();
	}

	public void addNewDesenvolvedorRequisitoSprint(DesenvolvedorRequisitoSprint drs) {
		dao.addnewDesenvolvedorRequisitoSprint(drs);
	}

}
