package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.TarefaDAO;
import model.Tarefa;
import model.Tarefa;
import model.Tarefa;

public class TarefaController {

	private TarefaDAO tarefaDAO;

	public TarefaController() throws IOException {
		tarefaDAO = new TarefaDAO();
	}

	public List<Tarefa> enviarListaTarefa() {
		return tarefaDAO.retornarListaTarefas();
	}

	public void addNewTarefa(Tarefa tarefa) {
		tarefaDAO.addNewTarefa(tarefa);
	}

	public List<Tarefa> retornarListaTarefasDisponiveis() {
		return tarefaDAO.retornarListaTarefasDisponiveis();
	}

	public List<Tarefa> retornarListaTarefasVinculados(int idRelease) {
		return tarefaDAO.retornarListaTarefasVinculados(idRelease);
	}

	public void vincularTarefaARelease(List<Tarefa> tarefa, int idRelease) throws InterruptedException {
		tarefaDAO.vincularTarefaARelease(tarefa, idRelease);
	}

	public void desvincularTarefaARelease(List<Tarefa> tarefa) throws InterruptedException {
		tarefaDAO.desvincularTarefaARelease(tarefa);
	}

	public List<Tarefa> retornarListaTarefasDeRelease(int idRelease) {
		List<Tarefa> listaComTodasTarefas = retornarListaTarefasDisponiveis();
		List<Tarefa> listaComTodasTarefasDaRelease = new ArrayList<>();
		for (Tarefa tarefa : listaComTodasTarefas) {
			if (tarefa.getIdRelease() == idRelease) {
				listaComTodasTarefasDaRelease.add(tarefa);
			}
		}
		return listaComTodasTarefasDaRelease;
	}
}
