package model.dao;

import java.util.List;

import model.ModelException;
import model.Servico;

public interface DAOServico {
	boolean save(Servico servico) throws ModelException ;
	boolean update(Servico servico) throws ModelException;
	boolean delete(Servico servico) throws ModelException;
	List<Servico> listAll() throws ModelException;
	Servico findById(int id) throws ModelException;
}
