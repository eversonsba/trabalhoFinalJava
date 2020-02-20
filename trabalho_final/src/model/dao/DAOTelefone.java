package model.dao;

import java.util.List;

import model.ModelException;
import model.Telefone;

public interface DAOTelefone {
	boolean save(Telefone telefone) throws ModelException ;
	boolean update(Telefone telefone) throws ModelException;
	boolean delete(Telefone telefone) throws ModelException;
	List<Telefone> listAll() throws ModelException;
	Telefone findByI(int id) throws ModelException;
}
