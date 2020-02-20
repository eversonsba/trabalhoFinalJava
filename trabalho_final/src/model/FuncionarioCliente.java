package model;

public class FuncionarioCliente {
	
	public FuncionarioCliente(int id) {
		this.id=id;
	}

	private int id;
	private String nome;
	private Cliente cliente;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public int getId() {
		return id;
	}
	
	
}
