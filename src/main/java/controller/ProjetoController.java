package controller;

import java.io.IOException;
import java.util.List;

import db.ProjetoDAO;
import model.Projeto;

public class ProjetoController {

	private ProjetoDAO dao;

	public ProjetoController() throws IOException {
		dao = new ProjetoDAO();
	}

	public List<Projeto> enviarListaProjetos() {
		return dao.retornarListarProjetos();
	}

	public void cadastrarNovoProjeto(Projeto projeto) {
		dao.addNewProjeto(projeto);
	}

	public void editarProjeto(Projeto projeto) {
		dao.editarDadosProjeto(projeto);
	}

	public void removerProjeto(Projeto projeto) {
		dao.removerProjeto(projeto);
	}
}
