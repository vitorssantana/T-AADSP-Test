package model;

public class Projeto {

	private int id;
	private String nome;
	private int idStakeholder;
	private double custo;
	private String prazoEntrega;

	public Projeto() {
		
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

	public int getIdStakeholder() {
		return idStakeholder;
	}

	public void setIdStakeholder(int idStakeholder) {
		this.idStakeholder = idStakeholder;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public String getPrazoEntrega() {
		return prazoEntrega;
	}

	public void setPrazoEntrega(String prazoEntrega) {
		this.prazoEntrega = prazoEntrega;
	}

}
