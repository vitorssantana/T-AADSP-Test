package model;

import java.util.ArrayList;
import java.util.List;

public class Sprint {

	private int id;
	private String nome, dataInicio, dataFim, status;
	private List<Bug> listaBugs;
	private List<Tarefa> listaTarefas;

	public Sprint() {
		listaBugs = new ArrayList<Bug>();
		listaTarefas = new ArrayList<Tarefa>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setListaBugs(List<Bug> listaBugs) {
		this.listaBugs = listaBugs;
	}

	public void setListaTarefas(List<Tarefa> listaTarefas) {
		this.listaTarefas = listaTarefas;
	}

}
