package model;

import java.util.Date;

public class Servico {
	private int id;
	private String descricao;
	private double valor;
	private Date data;
	private Cliente cliente;
	private Complemento complemento = null;
	private FuncionarioCliente funcionarioCliente = null;
	
	public Servico(int id) {
		this.id=id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Complemento getComplemento() {
		return complemento;
	}
	public void setComplemento(Complemento complemento) {
		this.complemento = complemento;
	}
	public FuncionarioCliente getFuncionarioCliente() {
		return funcionarioCliente;
	}
	public void setFuncionarioCliente(FuncionarioCliente funcionarioCliente) {
		this.funcionarioCliente = funcionarioCliente;
	}
	public int getId() {
		return id;
	}
}