package model.dao;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.FuncionarioCliente;
import model.ModelException;
import model.Servico;
import sun.font.CreatedFontTracker;

public class MySQLFuncionarioCliente implements DAOFuncionarioCliente {

	@Override
	public boolean save(FuncionarioCliente funcionarioCliente) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO funcionario_cliente VALUES "
				+ " (DEFAULT, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, funcionarioCliente.getNome());
		db.setString(2, funcionarioCliente.getCliente().getCnpj());
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(FuncionarioCliente funcionarioCliente) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE funcionario_cliente "
						 + " SET nome = ? ,"
						 + "cnpj_cliente = ?"
						 + " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		
		db.setString(1, funcionarioCliente.getNome());
		db.setString(2, funcionarioCliente.getCliente().getCnpj());
		db.setInt(3,funcionarioCliente.getId());
				
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(FuncionarioCliente funcionarioCliente) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = " DELETE FROM funcionario_cliente "
		         + " WHERE id = ?;";

		db.prepareStatement(sqlDelete);		
		db.setInt(1,funcionarioCliente.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public List<FuncionarioCliente> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<FuncionarioCliente> funcionarios = new ArrayList<FuncionarioCliente>();
		
		String sqlQuery = "SELECT f.id,f.nome,c.cnpj,c.nome as cliente FROM funcionario_cliente f INNER JOIN clientes c ON c.cnpj = f.cnpj_cliente;";

		db.createStatement();

		db.executeQuery(sqlQuery);

while (db.next()) {
	FuncionarioCliente f = creatFuncionario(db);
	
	funcionarios.add(f);
}

return funcionarios;
		
	}

	@Override
	public FuncionarioCliente findByI(int id) throws ModelException {
		DBHandler db = new DBHandler();
				
		String sqlQuery = "SELECT f.id,f.nome,c.cnpj,c.nome as cliente FROM funcionario_cliente f INNER JOIN clientes c ON c.cnpj = f.cnpj_cliente WHERE f.id = "+id+";";
		db.createStatement();
		
		db.executeQuery(sqlQuery);

		FuncionarioCliente f = null;
		while (db.next()) {
			f = creatFuncionario(db);
			break;
		}
		return f;
	}

	
	private FuncionarioCliente creatFuncionario(DBHandler db) throws ModelException {
		
		FuncionarioCliente fc = new FuncionarioCliente(db.getInt("id"));
		fc.setNome(db.getString("nome"));
		
		DAOCliente clienteDao = DAOFactory.createDAO(DAOCliente.class); 
		
		Cliente c = clienteDao.findByCNPJ(db.getString("cnpj"));
		
		fc.setCliente(c);	
		return fc;
	}
}


