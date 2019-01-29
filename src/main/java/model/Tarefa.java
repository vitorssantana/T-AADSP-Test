package model;

public class Tarefa {

	private Integer id, idDesenvolvedor, idRelease = null, idRequisito;
	private String titulo, nivelImpacto, prioridade;

	public Tarefa() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdDesenvolvedor() {
		return idDesenvolvedor;
	}

	public void setIdDesenvolvedor(Integer idDesenvolvedor) {
		this.idDesenvolvedor = idDesenvolvedor;
	}

	public Integer getIdRelease() {
		return idRelease;
	}

	public void setIdRelease(Integer idRelease) {
		this.idRelease = idRelease;
	}

	public Integer getIdRequisito() {
		return idRequisito;
	}

	public void setIdRequisito(Integer idRequisito) {
		this.idRequisito = idRequisito;
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


	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

}
