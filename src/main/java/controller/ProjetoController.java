package controller;

import java.io.IOException;
import java.util.List;

import db.ProjetoDAO;
import model.Projeto;

public class ProjetoController {

	private ProjetoDAO projetoDAO;

	public ProjetoController() throws IOException {
		projetoDAO = new ProjetoDAO();
	}

	public List<Projeto> enviarListaProjetos() {
		return projetoDAO.retornarListarProjetos();
	}

	public void cadastrarNovoProjeto(Projeto projeto) {
		projetoDAO.addNewProjeto(projeto);
	}
	
	public void editarProjeto(Projeto projeto) {
		projetoDAO.editarDadosProjeto(projeto);
	}

}
