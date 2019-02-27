package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.BugDAO;
import db.BugDAO;
import model.Bug;
import model.Bug;

public class BugController {

	private BugDAO bugDAO;

	public BugController() throws IOException {
		bugDAO = new BugDAO();
	}

	public List<Bug> enviarListaBug() {
		return bugDAO.retornarListaBugs();
	}

	public void addNewBug(Bug bug) {
		bugDAO.addNewBug(bug);
	}

	public List<Bug> retornarListaBugsDisponiveis() {
		return bugDAO.retornarListaBugsDisponiveis();
	}



	public List<Bug> retornarListaBugsDeRelease(int idRelease) {
		List<Bug> listaComTodosBugs = retornarListaBugsDisponiveis();
		List<Bug> listaComTodosBugsDaRelease = new ArrayList<Bug>();
		for (Bug bug : listaComTodosBugs) {
			if (bug.getIdRelease() == idRelease) {
				listaComTodosBugsDaRelease.add(bug);
			}
		}
		return listaComTodosBugsDaRelease;
	}

}
