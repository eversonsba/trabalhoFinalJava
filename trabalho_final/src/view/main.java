package view;

import model.Cliente;
import model.Complemento;
import model.FuncionarioCliente;
import model.ModelException;
import model.Servico;
import model.dao.MySQLClienteDAO;
import model.dao.MySQLComplemento;
import model.dao.MySQLFuncionarioCliente;
import model.dao.MySQLServico;

public class main {

	public static void main(String[] args) throws ModelException {
		MySQLClienteDAO daoc = new MySQLClienteDAO();
		Cliente c = daoc.findByCNPJ("12345678901234");
		
		MySQLComplemento daocp = new MySQLComplemento();
		Complemento cp = daocp.findById(1);
		MySQLFuncionarioCliente daofc = new MySQLFuncionarioCliente();
		FuncionarioCliente fc = daofc.findByI(1);
		
		MySQLServico daos = new MySQLServico();
		Servico s = new Servico(0);
////		daos.delete(s);
//		s.setCliente(c);
//		s.setDescricao("Teste aula2");
//		s.setValor(97.5);
//	//	s.setComplemento(cp);
////		s.setFuncionarioCliente(fc);
//		daos.save(s);
		
		for(Servico s1 : daos.listAll()) {
			System.out.println(s1.getId()+s1.getDescricao()+s1.getValor());
		}
	}
		
	}
