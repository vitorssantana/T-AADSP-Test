package model;

public class PredicaoTeste {

	public int getIdSprint() {
		return idSprint;
	}

	public void setIdSprint(int idSprint) {
		this.idSprint = idSprint;
	}

	private double probabilidadeAlta, probabilidadeMedia, probabilidadeBaixa;
	private int idRequisito, idSprint, idProjeto;
	private String nomeRequisito, nomeProjeto;

	public PredicaoTeste() {
		// TODO Auto-generated constructor stub
	}

	public double getProbabilidadeAlta() {
		return probabilidadeAlta;
	}

	public void setProbabilidadeAlta(double probabilidadeAlta) {
		this.probabilidadeAlta = probabilidadeAlta;
	}

	public double getProbabilidadeMedia() {
		return probabilidadeMedia;
	}

	public void setProbabilidadeMedia(double probabilidadeMedia) {
		this.probabilidadeMedia = probabilidadeMedia;
	}

	public double getProbabilidadeBaixa() {
		return probabilidadeBaixa;
	}

	public void setProbabilidadeBaixa(double probabilidadeBaixa) {
		this.probabilidadeBaixa = probabilidadeBaixa;
	}

	public int getIdRequisito() {
		return idRequisito;
	}

	public void setIdRequisito(int idRequisito) {
		this.idRequisito = idRequisito;
	}

	public String getNomeRequisito() {
		return nomeRequisito;
	}

	public void setNomeRequisito(String nomeRequisito) {
		this.nomeRequisito = nomeRequisito;
	}

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public int getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}
}
