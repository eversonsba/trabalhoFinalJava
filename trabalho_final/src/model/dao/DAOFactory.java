package model.dao;

import java.util.HashMap;
import java.util.Map;

public class DAOFactory {
	
	private static Map<Class, Object> listDAOsInterfaces = new HashMap<Class, Object>();
	
	// Para o DAOFactory funcionar para suas classes de domínio, adicione na 
	// lista suas interfaces e classes DAO na listDAOsInterfaces
	static {
		listDAOsInterfaces.put(DAOCliente.class, new MySQLClienteDAO());
		listDAOsInterfaces.put(DAOComplemento.class, new MySQLComplemento());
		listDAOsInterfaces.put(DAOEnderecos.class, new MySQLEnderecos());
		listDAOsInterfaces.put(DAOFuncionarioCliente.class, new MySQLFuncionarioCliente());
		listDAOsInterfaces.put(DAOServico.class, new MySQLServico());
		listDAOsInterfaces.put(DAOTelefone.class, new MySQLTelefone());
	}
	
	@SuppressWarnings("unchecked")
	public static <DAOInterface> DAOInterface createDAO(Class<?> entity){
		return (DAOInterface) listDAOsInterfaces.get(entity);
	} 
}