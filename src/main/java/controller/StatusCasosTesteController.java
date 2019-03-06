package controller;

import java.io.IOException;
import java.util.List;

import db.StatusCasosTesteDAO;
import model.StatusCasosTeste;

public class StatusCasosTesteController {

	StatusCasosTesteDAO dao;

	public StatusCasosTesteController() throws IOException {
		dao = new StatusCasosTesteDAO();
	}

	public List<StatusCasosTeste> retornarListaStatusCasosTeste() {
		return dao.retornarListaStatusCasosTestes();
	}

	public void addStatusCasosTeste(StatusCasosTeste statusCasosTeste) {
		dao.addNewStatusCasosTeste(statusCasosTeste);
	}

	public void editarStatusCasosTeste(StatusCasosTeste statusCasosTeste) {
		dao.editarNewStatusCasosTeste(statusCasosTeste);
	}
}
