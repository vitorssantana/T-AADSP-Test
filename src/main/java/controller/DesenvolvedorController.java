package controller;

import java.io.IOException;
import java.util.List;

import db.DesenvolvedorDAO;
import model.Desenvolvedor;

public class DesenvolvedorController {

	private DesenvolvedorDAO desenvolvedorDAO;

	public DesenvolvedorController() throws IOException {
		desenvolvedorDAO = new DesenvolvedorDAO();
	}

	public List<Desenvolvedor> enviarListaDesenvolvedor() {
		return desenvolvedorDAO.retornarListaDesenvolvedores();
	}
	
	public void addNewDesenvolvedor(Desenvolvedor desenvolvedor) {
		desenvolvedorDAO.addNewDesenvolvedor(desenvolvedor);
	}
}
