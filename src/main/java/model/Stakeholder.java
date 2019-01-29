package model;

public class Stakeholder {

	private int id;
	private String nome;
	private int notaPrioridade;

	public Stakeholder() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getNotaPrioridade() {
		return notaPrioridade;
	}

	public void setNotaPrioridade(int notaPrioridade) {
		this.notaPrioridade = notaPrioridade;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
