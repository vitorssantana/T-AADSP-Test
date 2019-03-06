package model;

public class Bug {

	private Integer id, idDesenvolvedor, idRequisitoSprint;
	private String titulo, nivelImpacto, descricao;

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

	public String toString() {
		return id + " - " + titulo;
	}

	public Integer getIdRequisitoSprint() {
		return idRequisitoSprint;
	}

	public void setIdRequisitoSprint(Integer idRequisitoSprint) {
		this.idRequisitoSprint = idRequisitoSprint;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdDesenvolvedor(Integer idDesenvolvedor) {
		this.idDesenvolvedor = idDesenvolvedor;
	}

}
