package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.BugDAO;
import db.BugDAO;
import model.Bug;
import model.Bug;

public class BugController {

	private BugDAO dao;

	public BugController() throws IOException {
		dao = new BugDAO();
	}

	public List<Bug> enviarListaBug() {
		return dao.retornarListaBugs();
	}

	public void addNewBug(Bug bug) {
		dao.addNewBug(bug);
	}
	
	public void editarBug(Bug bug) {
		dao.editarBug(bug);
	}

	public void removerBug(Bug bug) {
		dao.removerBug(bug);
	}
}
