package controller;

import java.io.IOException;
import java.util.List;

import db.PlanoTesteDAO;
import model.PlanoTeste;

public class PlanoTesteController {

	private PlanoTesteDAO planoTesteDAO;

	public PlanoTesteController() throws IOException {
		planoTesteDAO = new PlanoTesteDAO();
	}

	public List<PlanoTeste> enviarListaPlanoTeste() {
		return planoTesteDAO.retornarListaPlanoTestes();
	}

	public void addNewPlanoTeste(PlanoTeste planoTeste) {
		planoTesteDAO.addNewPlanoTeste(planoTeste);
	}

}
