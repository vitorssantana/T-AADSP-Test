package model;

public class RequisitoSprint {

	private Integer id, idRequisito, idSprint;

	private String nivelImpactoAlteracoes;

	public Boolean getIsVinculouDesenvolvedor() {
		return isVinculouDesenvolvedor;
	}

	public void setIsVinculouDesenvolvedor(Boolean isVinculouDesenvolvedor) {
		this.isVinculouDesenvolvedor = isVinculouDesenvolvedor;
	}

	private Boolean isVinculouDesenvolvedor;

	public RequisitoSprint() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdRequisito() {
		return idRequisito;
	}

	public void setIdRequisito(Integer idRequisito) {
		this.idRequisito = idRequisito;
	}

	public Integer getIdSprint() {
		return idSprint;
	}

	public void setIdSprint(Integer idSprint) {
		this.idSprint = idSprint;
	}

	public Boolean isVinculouDesenvolvedor() {
		return isVinculouDesenvolvedor;
	}

	public void setVinculouDesenvolvedor(boolean isVinculouDesenvolvedor) {
		this.isVinculouDesenvolvedor = isVinculouDesenvolvedor;
	}

	public String getNivelImpactoAlteracoes() {
		return nivelImpactoAlteracoes;
	}

	public void setNivelImpactoAlteracoes(String nivelImpactoAlteracoes) {
		this.nivelImpactoAlteracoes = nivelImpactoAlteracoes;
	}

}
