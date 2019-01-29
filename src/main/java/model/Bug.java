package model;

public class Bug {

	private Integer id, idDesenvolvedor, idRelease, idTarefaGeradora, idBugGerador;
	private String titulo, nivelImpacto, descricao, prioridade;

	public Bug() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDesenvolvedor() {
		return idDesenvolvedor;
	}

	public void setIdDesenvolvedor(int idDesenvolvedor) {
		this.idDesenvolvedor = idDesenvolvedor;
	}

	public Integer getIdRelease() {
		return idRelease;
	}

	public void setIdRelease(int idRelease) {
		this.idRelease = idRelease;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNivelImpacto() {
		return nivelImpacto;
	}

	public void setNivelImpacto(String nivelImpacto) {
		this.nivelImpacto = nivelImpacto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getIdTarefaGeradora() {
		return idTarefaGeradora;
	}

	public void setIdTarefaGeradora(int idTarefaGeradora) {
		if (idTarefaGeradora == 0)
			this.idTarefaGeradora = (Integer) null;
		else
			this.idTarefaGeradora = idTarefaGeradora;
	}

	public Integer getIdBugGerador() {
		return idBugGerador;
	}

	public void setIdBugGerador(int idBugGerador) {
		if (idBugGerador == 0)
			this.idBugGerador = (Integer) null;
		else
			this.idBugGerador = idBugGerador;
	}
	
	public String toString() {
		return id + " - " + titulo;
	}
	
}
