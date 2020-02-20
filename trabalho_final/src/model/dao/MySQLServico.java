package model.dao;
import java.sql.SQLPermission;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Complemento;
import model.FuncionarioCliente;
import model.ModelException;
import model.Servico;

public class MySQLServico implements DAOServico {

	@Override
	public boolean save(Servico servico) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO servico VALUES "
				+ " (DEFAULT,?,?,CURDATE(), ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, servico.getDescricao());
		db.setDouble(2, servico.getValor());
		db.setString(3, servico.getCliente().getCnpj());
		
		if((servico.getComplemento()==null)) {
			String def = "DEFAULT";
		 db.setNull(4);
		}else {
			db.setInt(4,servico.getComplemento().getId());
		}
		
		if((servico.getFuncionarioCliente())==null) {
			String def = "DEFAULT";
			db.setNull(5);
		}else {
			db.setInt(5, servico.getFuncionarioCliente().getId());
		}
		System.out.println(sqlInsert);
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Servico servico) throws ModelException {
		DBHandler db = new DBHandler();
		String sqlUpdate = "UPDATE servico "
				 + " SET descricao = ? ,"
				 + "valor = ? "
				 + " WHERE id = ?;";

		db.prepareStatement(sqlUpdate);

		db.setString(1,servico.getDescricao());
		db.setDouble(2,servico.getValor());
		db.setInt(3, servico.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Servico servico) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "DELETE FROM servico"
				+ " WHERE id = ?;";
		
		db.prepareStatement(sqlInsert);
		db.setInt(1, servico.getId());
		
		  
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Servico> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		
		List<Servico> servicos = new ArrayList<Servico>();
		
		String sqlQuery = "SELECT s.id,s.descricao as servico,s.valor,s.data,c.cnpj,c.nome as cliente,cp.id as complemento,cp.descricao,f.id as funcionario,f.nome FROM servico s " + 
				" LEFT JOIN clientes c" + 
				" ON c.cnpj = s.cnpj_cliente" + 
				" LEFT JOIN" + 
				" complementos cp" + 
				" ON cp.id = s.id_complemento" + 
				" LEFT JOIN funcionario_cliente f" + 
				" ON s.id_funcionario_cliente = f.id;";
		db.createStatement();

		db.executeQuery(sqlQuery);

while (db.next()) {
	Servico s = createServico(db);
	
	servicos.add(s);
	}
return servicos;
	}

	@Override
	public Servico findById(int id) throws ModelException {
			DBHandler db = new DBHandler();
			String sqlQuery = "SELECT s.id,s.descricao as servico,s.valor,s.data,c.cnpj,c.nome as cliente,cp.id as complemento,cp.descricao,f.id as funcionario,f.nome FROM servico s " + 
					" LEFT JOIN clientes c" + 
					" ON c.cnpj = s.cnpj_cliente" + 
					" LEFT JOIN" + 
					" complementos cp" + 
					" ON cp.id = s.id_complemento" + 
					" LEFT JOIN funcionario_cliente f" + 
					" ON s.id_funcionario_cliente = f.id"+
					" WHERE s.id  ="+id+";";
			db.createStatement();
			db.executeQuery(sqlQuery);
			
			Servico s = null;
			while (db.next()) {
				s = createServico(db);
				break;
			}
			
			return s;
					
				}
	
	
	private Servico createServico(DBHandler db) throws ModelException {
		Servico s = new Servico(db.getInt("id"));
		s.setDescricao(db.getString("servico"));
		s.setValor(db.getDouble("valor"));
		s.setData(db.getDate("data"));
				
		DAOCliente clienteDao = DAOFactory.createDAO(DAOCliente.class); 
		
		Cliente c = clienteDao.findByCNPJ(db.getString("cnpj"));
		
		DAOComplemento complemento = DAOFactory.createDAO(DAOComplemento.class); 
		
		Complemento cp = complemento.findById(db.getInt("complemento"));
		
		DAOFuncionarioCliente daoFuncionario= DAOFactory.createDAO(DAOFuncionarioCliente.class); 
		
		FuncionarioCliente fc = daoFuncionario.findByI(db.getInt("funcionario"));
		
		s.setCliente(c);
		
		s.setComplemento(cp);
		
		s.setFuncionarioCliente(fc);
		
		return s;
	}
	}

