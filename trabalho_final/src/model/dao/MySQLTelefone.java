package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Endereco;
import model.FuncionarioCliente;
import model.ModelException;
import model.Telefone;

public class MySQLTelefone implements DAOTelefone {

	@Override
	public boolean save(Telefone telefone) throws ModelException {
DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO telefones VALUES "
				+ " (DEFAULT,?,?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, telefone.getNumero());
		db.setString(2, telefone.getCliente().getCnpj());
			  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Telefone telefone) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE telefones "
						 + " SET numero = ? "
						 + " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		db.setString(1,telefone.getNumero());
		db.setInt(2, telefone.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Telefone telefone) throws ModelException {
DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM telefones "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1,telefone.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Telefone> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Telefone> telefones = new ArrayList<Telefone>();
		
		String sqlQuery = "SELECT t.id,t.numero,c.cnpj,c.nome " + 
				"FROM telefones t " + 
				"INNER JOIN clientes c " + 
				"WHERE t.cnpj_cliente = c.cnpj;";

		db.createStatement();

		db.executeQuery(sqlQuery);

while (db.next()) {
	Telefone t = createTelefone(db);
	
	telefones.add(t);
}

return telefones;
		
	}

	@Override
	public Telefone findByI(int id) throws ModelException {
DBHandler db = new DBHandler();
		
		String sql = "SELECT t.id,t.numero,c.cnpj,c.nome" + 
				" FROM telefones t" + 
				" INNER JOIN clientes c" + 
				" ON t.cnpj_cliente = c.cnpj"+
				" WHERE t.id ="+id+";";
		
		db.prepareStatement(sql);
		db.executeQuery();
		
		Telefone t = null;
		while (db.next()) {
			t = createTelefone(db);
			break;
		}
		return t;
	}
	
	private Telefone createTelefone(DBHandler db) throws ModelException {
		Telefone t = new Telefone(db.getInt("id"));
		t.setNumero(db.getString("numero"));
		
		DAOCliente clienteDao = DAOFactory.createDAO(DAOCliente.class); 
		
		Cliente c = clienteDao.findByCNPJ(db.getString("cnpj"));
		
		t.setCliente(c);
		
		return t;
	}

	}