package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.ModelException;

public class MySQLClienteDAO implements DAOCliente {

	@Override
	public boolean save(Cliente cliente) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO clientes VALUES" + "(?,?)";

		db.prepareStatement(sqlInsert);
		db.setString(1, cliente.getCnpj());
		db.setString(2, cliente.getNome());
		System.out.println(sqlInsert);
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Cliente cliente) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE clientes" + " SET nome= ? " + " WHERE cnpj = ?;";
		
		db.prepareStatement(sqlUpdate);

		db.setString(1, cliente.getNome());
		db.setString(2, cliente.getCnpj());
		System.out.println(sqlUpdate);
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Cliente cliente) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = " DELETE FROM clientes " + " WHERE cnpj = ?;";

		db.prepareStatement(sqlDelete);
		db.setString(1, cliente.getCnpj());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Cliente> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Cliente> clientes = new ArrayList<Cliente>();

		// Declara um instrução SQL
		String sqlQuery = " SELECT * FROM clientes; ";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			Cliente c = createCliente(db);

			clientes.add(c);
		}

		return clientes;
	}

	@Override
	public Cliente findByCNPJ(String cnpj) throws ModelException {

		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM clientes WHERE cnpj = ?;";

		db.prepareStatement(sql);
		db.setString(1, cnpj);
		System.out.println(sql);
		db.executeQuery();

		Cliente c = null;
		while (db.next()) {
			c = createCliente(db);
			break;
		}

		return c;
	}

	private Cliente createCliente(DBHandler db) throws ModelException {
		Cliente c = new Cliente();
		c.setCnpj(db.getString("cnpj"));
		c.setNome(db.getString("nome"));
		return c;
	}

}
