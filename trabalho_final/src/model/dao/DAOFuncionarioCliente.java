package model.dao;

import java.util.List;

import model.FuncionarioCliente;
import model.ModelException;

public interface DAOFuncionarioCliente {
	boolean save(FuncionarioCliente funcionarioCliente) throws ModelException ;
	boolean update(FuncionarioCliente funcionarioCliente) throws ModelException;
	boolean delete(FuncionarioCliente funcionarioCliente) throws ModelException;
	List<FuncionarioCliente> listAll() throws ModelException;
	FuncionarioCliente findByI(int id) throws ModelException;
}
