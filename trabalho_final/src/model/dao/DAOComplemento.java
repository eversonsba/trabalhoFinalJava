package model.dao;

import java.util.List;
import model.Complemento;
import model.ModelException;

public interface DAOComplemento {
	boolean save(Complemento complemento) throws ModelException ;
	boolean update(Complemento complemento) throws ModelException;
	boolean delete(Complemento complemento) throws ModelException;
	List<Complemento> listAll() throws ModelException;
	Complemento findById(int id) throws ModelException;
}
