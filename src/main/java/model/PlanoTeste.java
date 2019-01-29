package model;

public class PlanoTeste {

	private Integer id, idRequisito;
	private String descricao, tipoTeste;

	public PlanoTeste() {
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoTeste() {
		return tipoTeste;
	}

	public void setTipoTeste(String tipoTeste) {
		this.tipoTeste = tipoTeste;
	}

}
