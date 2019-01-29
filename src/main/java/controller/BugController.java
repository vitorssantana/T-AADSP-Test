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

	public List<Bug> retornarListaBugsVinculados(int idRelease) {
		return bugDAO.retornarListaBugsVinculados(idRelease);
	}

	public void vincularBugARelease(List<Bug> bug, int idRelease) throws InterruptedException {
		bugDAO.vincularBugARelease(bug, idRelease);
	}

	public void desvincularBugARelease(List<Bug> bug) throws InterruptedException {
		bugDAO.desvincularBugARelease(bug);
	}

	public List<Bug> retornarListaBugsDeRelease(int idRelease) {
		List<Bug> listaComTodosBugs = retornarListaBugsDisponiveis();
		List<Bug> listaComTodosBugsDaRelease = new ArrayList<>();
		for (Bug bug : listaComTodosBugs) {
			if (bug.getIdRelease() == idRelease) {
				listaComTodosBugsDaRelease.add(bug);
			}
		}
		return listaComTodosBugsDaRelease;
	}

}
