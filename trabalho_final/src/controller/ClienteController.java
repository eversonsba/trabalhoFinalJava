package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import model.ModelException;
import model.dao.DAOCliente;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/clientes", "/cliente/form", "/cliente/delete", "/cliente/insert", "/cliente/update"})
public class ClienteController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/ProjetoFinalJava/cliente/form": {
			listClientes(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-cliente.jsp");
			break;
		}
		case "/ProjetoFinalJava/cliente/update": {
			listClientes(req);
			Cliente cliente = loadCliente(req);
			req.setAttribute("cliente", cliente);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-cliente.jsp");
			break;
		}
		default:
			listClientes(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/clientes.jsp");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		if (action == null || action.equals("") ) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
		case "/ProjetoFinalJava/cliente/delete":
			deleteCliente(req, resp);
			break;	
		case "/ProjetoFinalJava/cliente/insert": {
			insertCliente(req, resp);
			break;
		}
		case "/ProjetoFinalJava/cliente/update": {
			updateCliente(req, resp);
			break;
		}
		default:
			System.out.println("URL inv·lida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/clientes");
	}

	private Cliente loadCliente(HttpServletRequest req) {
		String clienteCnpj = req.getParameter("id");
			
		DAOCliente dao = DAOFactory.createDAO(DAOCliente.class);
		
		try {
			Cliente cliente = dao.findByCNPJ(clienteCnpj);
			
			if (cliente == null)
				throw new ModelException("Cliente n„o encontrado para alteraÁ„o");
			
			return cliente;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}
	
	private void listClientes(HttpServletRequest req) {
		DAOCliente dao = DAOFactory.createDAO(DAOCliente.class);
		
		List<Cliente> clientes = null;
		try {
			clientes = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (clientes != null)
			req.setAttribute("clientes", clientes);
	}
	
	private void insertCliente(HttpServletRequest req, HttpServletResponse resp) {
		String cnpj = req.getParameter("cnpj");
		String nome = req.getParameter("nome");
		
		Cliente cliente = new Cliente();
		
		cliente.setCnpj(cnpj);
		cliente.setNome(nome);
		
		DAOCliente dao = DAOFactory.createDAO(DAOCliente.class);
		
		try {
			if (dao.save(cliente)) {
				ControllerUtil.sucessMessage(req, "Cliente '" + cliente.getNome() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Cliente '" + cliente.getNome() + "'  n„o pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateCliente(HttpServletRequest req, HttpServletResponse resp) {
		String cnpj = req.getParameter("id");
		String nome = req.getParameter("nome");
		
		Cliente cliente = new Cliente();
		
		cliente.setCnpj(cnpj);
		cliente.setNome(nome);
		
		DAOCliente dao = DAOFactory.createDAO(DAOCliente.class);
		
		try {
			if (dao.update(cliente)) {
				ControllerUtil.sucessMessage(req, "Usu·rio '" + cliente.getNome() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Usu·rio '" + cliente.getNome() + "' n„o pode ser atualizado.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void deleteCliente(HttpServletRequest req, HttpServletResponse resp) {
		String cnpj = req.getParameter("id");
				
		DAOCliente dao = DAOFactory.createDAO(DAOCliente.class);
		
		try {
			Cliente cliente = dao.findByCNPJ(cnpj);
			
			if (cliente == null)
				throw new ModelException("Usu·rio n√£o encontrado para dele√ß√£o.");
			
			if (dao.delete(cliente)) {
				ControllerUtil.sucessMessage(req, "Usu·rio '" + cliente.getNome() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Usu·rio '" + cliente.getNome() + "' n„o pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
