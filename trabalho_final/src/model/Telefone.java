package model;

public class Telefone {
	private int id;
	private String numero;
	private Cliente cliente;
	
	public Telefone(int id) {
		this.id=id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public int getId() {
		return id;
	}public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

}
