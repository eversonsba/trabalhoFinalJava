package model.dao;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Complemento;
import model.ModelException;
import model.Servico;

public class MySQLComplemento implements DAOComplemento {

	@Override
	public boolean save(Complemento complemento) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO complementos VALUES" 
		+"(DEFAULT,?)";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, complemento.getDescricao());
		
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Complemento complemento) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE complementos"
						 + " SET descricao= ? "
						 + " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, complemento.getDescricao());
		db.setInt(2, complemento.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Complemento complemento) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM complementos "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1,complemento.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Complemento> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Complemento> complementos = new ArrayList<Complemento>();

		// Declara um instrução SQL
		String sqlQuery = " SELECT * FROM complementos; ";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			Complemento c = createComplemento(db);

			complementos.add(c);
		}

		return complementos;
	}

	@Override
	public Complemento findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlQuery = "SELECT id, descricao FROM complementos WHERE id ="+id+";";
		db.createStatement();

		db.executeQuery(sqlQuery);
		
		Complemento c = null;
		while (db.next()) {
			c = createComplemento(db);
			break;
		}

				return c;
	}

	private Complemento createComplemento(DBHandler db) throws ModelException {
		Complemento c = new Complemento(db.getInt("id"));
	
		c.setDescricao(db.getString("descricao"));
		
		return c;
	}
	
	
	}

