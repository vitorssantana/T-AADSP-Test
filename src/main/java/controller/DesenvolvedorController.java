package controller;

import java.io.IOException;
import java.util.List;

import db.DesenvolvedorDAO;
import model.Desenvolvedor;
import model.Projeto;

public class DesenvolvedorController {

	private DesenvolvedorDAO dao;

	public DesenvolvedorController() throws IOException {
		dao = new DesenvolvedorDAO();
	}

	public List<Desenvolvedor> enviarListaDesenvolvedor() {
		return dao.retornarListaDesenvolvedores();
	}

	public void cadastrarNovoDesenvovedor(Desenvolvedor desenvolvedor) {
		dao.addNewDesenvolvedor(desenvolvedor);
	}

	public void editarDesenvolvedor(Desenvolvedor desenvolvedor) {
		dao.editarDadosDesenvolvedor(desenvolvedor);
	}
	
}
