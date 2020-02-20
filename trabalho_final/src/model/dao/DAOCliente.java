package model.dao;

import java.util.List;

import model.Cliente;
import model.ModelException;

public interface DAOCliente {
	boolean save(Cliente cliente) throws ModelException ;
	boolean update(Cliente cliente) throws ModelException;
	boolean delete(Cliente cliente) throws ModelException;
	List<Cliente> listAll() throws ModelException;
	Cliente findByCNPJ(String cnpj) throws ModelException;
}
