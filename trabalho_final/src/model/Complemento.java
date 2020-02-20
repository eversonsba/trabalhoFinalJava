package model;

public class Complemento {
	public int id;
	private String descricao;
	public Complemento(int id) {
		this.id=id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getId() {
		return id;
	}
	
}
