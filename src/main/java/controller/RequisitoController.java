package controller;

import java.io.IOException;
import java.util.List;

import db.RequisitoDAO;
import model.Requisito;

public class RequisitoController {

	private RequisitoDAO dao;

	public RequisitoController() throws IOException {
		dao = new RequisitoDAO();
	}

	public List<Requisito> enviarListaRequisitos() {
		return dao.retornarListarRequisitos();
	}

	public void cadastrarNovoRequisito(Requisito requisito) {
		dao.addNewRequisito(requisito);
	}

	public boolean verificarSeProjetoEstaVinculadoARequisito(int idProjeto) {
		List<Requisito> listaRequisitos = dao.retornarListarRequisitos();
		for (Requisito requisito : listaRequisitos) {
			if (requisito.getIdProjeto() == idProjeto)
				return true;
		}
		return false;
	}
}
