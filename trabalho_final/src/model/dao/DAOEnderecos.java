package model.dao;

import java.util.List;

import model.Endereco;
import model.ModelException;

public interface DAOEnderecos {
	boolean save(Endereco endereco) throws ModelException ;
	boolean update(Endereco endereco) throws ModelException;
	boolean delete(Endereco endereco) throws ModelException;
	List<Endereco> listAll() throws ModelException;
	Endereco findByI(int id) throws ModelException;
}
