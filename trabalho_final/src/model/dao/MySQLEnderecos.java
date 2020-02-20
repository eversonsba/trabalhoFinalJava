package model.dao;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Endereco;
import model.ModelException;
import model.Telefone;

public class MySQLEnderecos implements DAOEnderecos {

	@Override
	public boolean save(Endereco endereco) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO enderecos VALUES "
				+ " (DEFAULT,?,?,?,?);";
		
		db.prepareStatement(sqlInsert);
		db.setInt(1, endereco.getNumero());
		db.setString(2, endereco.getRua());
		db.setString(3, endereco.getCidade());
		db.setString(4, endereco.getCliente().getCnpj());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Endereco endereco) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE enderecos "
						 + " SET numero = ? ,"
						 + "rua = ?,"
						 + "cidade = ?"
						 + " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		
		db.setInt(1, endereco.getNumero());
		db.setString(2, endereco.getRua());
		db.setString(3, endereco.getCidade());
		db.setInt(4, endereco.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Endereco endereco) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM enderecos "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1,endereco.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Endereco> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Endereco> enderecos = new ArrayList<Endereco>();
		
		String sqlQuery = "SELECT e.id,e.numero,e.rua,e.cidade,c.cnpj,c.nome " + 
				"FROM enderecos e " + 
				"INNER JOIN clientes c " + 
				"ON e.cnpj_cliente = c.cnpj;";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
	Endereco e = createEndereco(db);
	
	enderecos.add(e);
}

return enderecos;
	}

	@Override
	public Endereco findByI(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT e.id,e.numero,e.rua,e.cidade,c.cnpj,c.nome\n" + 
				" FROM enderecos e \n" + 
				" INNER JOIN clientes c\n" + 
				" ON e.cnpj_cliente = c.cnpj\n" + 
				" WHERE e.id = "+id+";";
		
		db.prepareStatement(sql);
		db.executeQuery();
		
		Endereco e = null;
		while (db.next()) {
			e = createEndereco(db);
			break;
		}
		return e;
	}
	
	private Endereco createEndereco(DBHandler db) throws ModelException {
		Endereco e = new Endereco(db.getInt("id"));
		e.setCidade(db.getString("cidade"));
		e.setNumero(db.getInt("numero"));
		e.setRua(db.getString("rua"));
		DAOCliente clienteDao = DAOFactory.createDAO(DAOCliente.class); 
		
		Cliente c = clienteDao.findByCNPJ(db.getString("cnpj"));
		
		e.setCliente(c);
		
		return e;
	}

	}

