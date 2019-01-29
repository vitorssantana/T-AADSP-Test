package model;

import java.util.ArrayList;
import java.util.List;

public class Release {

	private int id;
	private String versao, dataEntrega;
	private List<Bug> listaBugs;
	private List<Tarefa> listaTarefas;

	public Release() {
		listaBugs = new ArrayList<Bug>();
		listaTarefas = new ArrayList<Tarefa>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public List<Bug> getListaBugs() {
		return listaBugs;
	}

	public List<Tarefa> getListaTarefas() {
		return listaTarefas;
	}

	public void addBug(Bug bug) {
		listaBugs.add(bug);
	}

	public void addTarefa(Tarefa tarefa) {
		listaTarefas.add(tarefa);
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

}
