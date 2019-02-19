package controller;

import java.io.IOException;
import java.util.List;

import db.PlanoTesteDAO;
import model.PlanoTeste;

public class PlanoTesteController {

	private PlanoTesteDAO dao;

	public PlanoTesteController() throws IOException {
		dao = new PlanoTesteDAO();
	}

	public List<PlanoTeste> enviarListaPlanoTeste() {
		return dao.retornarListaPlanoTestes();
	}

	public void addNewPlanoTeste(PlanoTeste planoTeste) {
		dao.addNewPlanoTeste(planoTeste);
	}

	public void editarTeste(PlanoTeste planoTeste) {
		dao.editarDadosTeste(planoTeste);
	}

}
