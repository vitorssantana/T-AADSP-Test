package model;

public class Requisito {

	private int id, idStakeholder, idProjeto, notaPrioridade;
	private String titulo;

	public Requisito() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdStakeholder() {
		return idStakeholder;
	}

	public void setIdStakeholder(int idStakeholder) {
		this.idStakeholder = idStakeholder;
	}

	public int getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}

	public int getNotaPrioridade() {
		return notaPrioridade;
	}

	public void setNotaPrioridade(int notaPrioridade) {
		this.notaPrioridade = notaPrioridade;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



}
