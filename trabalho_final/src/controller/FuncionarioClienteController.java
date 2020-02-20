package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import model.FuncionarioCliente;
import model.ModelException;
import model.dao.DAOCliente;
import model.dao.DAOFactory;
import model.dao.DAOFuncionarioCliente;

@SuppressWarnings("serial")
// Herda da classe HttpServlet (extends HttpServlet) para ser tratada como um Servlet
// Anota a classe (@WebServlet) ajustando as URLs (urlPatterns) as quais ela responde
@WebServlet(urlPatterns = {"/funcionarios", "/funcionario/form", "/funcionario/delete", "/funcionario/insert", "/funcionario/update"})
public class FuncionarioClienteController extends HttpServlet {
	
	// Sobrescreve o mÃ©todo doPost, sendo capaz de responder mÃ©todos HTTP POST
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/ProjetoFinalJava/funcionario/form": {
			listFuncionarios(req);
			listClientes(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-funcionario-cliente.jsp");
			break;
		}
		case "/ProjetoFinalJava/funcionario/update": {
			listClientes(req);
			FuncionarioCliente f = loadFuncionario(req);
			req.setAttribute("funcionario", f);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-funcionario-cliente.jsp");
			break;
		}
		default:
			listFuncionarios(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/index.jsp");
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
		case "/ProjetoFinalJava/funcionario/delete":
			deleteFuncionario(req, resp);
			break;
		case "/ProjetoFinalJava/funcionario/insert": {
			try {
				insertFuncionario(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "/ProjetoFinalJava/funcionario/update": {
			try {
				updateFuncionario(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		default:
			System.out.println("URL inválida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/funcionarios");
	}
	
	private FuncionarioCliente loadFuncionario(HttpServletRequest req) {
		String funcionarioIdParameter = req.getParameter("funcionarioId");
		
		int funcionarioId = Integer.parseInt(funcionarioIdParameter);
		
		DAOFuncionarioCliente dao = DAOFactory.createDAO(DAOFuncionarioCliente.class);
		
		try {
			FuncionarioCliente fc = dao.findByI(funcionarioId);
			
			if (fc == null)
				throw new ModelException("Funcionário não encontrado para alteração");
			
			return fc;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateFuncionario(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String nome = req.getParameter("nome");
		String cnpj_cliente = req.getParameter("cliente");
		
		FuncionarioCliente fc = loadFuncionario(req);
		fc.setNome(nome);
		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpj_cliente);
		fc.setCliente(c);
		
		DAOFuncionarioCliente dao = DAOFactory.createDAO(DAOFuncionarioCliente.class);
		
		try {
			if (dao.update(fc)) {
				ControllerUtil.sucessMessage(req, "Funcionario '" + fc.getNome() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Funcionario '" + fc.getNome() + "' não pode ser atualizado.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertFuncionario(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String nome = req.getParameter("nome");
		String cnpj_cliente = req.getParameter("cliente");
		FuncionarioCliente fc = new FuncionarioCliente(0);
		fc.setNome(nome);
		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpj_cliente);
		fc.setCliente(c);
		
		DAOFuncionarioCliente dao = DAOFactory.createDAO(DAOFuncionarioCliente.class);
		
		try {
			if (dao.save(fc)) {
				ControllerUtil.sucessMessage(req, "Funcionario '" + fc.getNome() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Funcionario '" + fc.getNome() + "' não pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
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

	private void deleteFuncionario(HttpServletRequest req, HttpServletResponse resp) {
		String clienteIdParameter = req.getParameter("id");
		
		int clienteId = Integer.parseInt(clienteIdParameter);
		
		DAOFuncionarioCliente dao = DAOFactory.createDAO(DAOFuncionarioCliente.class);
		
		try {
			FuncionarioCliente fc = dao.findByI(clienteId);
			
			if (fc == null)
				throw new ModelException("Funcionario não encontrado para deleção");
			
			if (dao.delete(fc)) {
				ControllerUtil.sucessMessage(req, "Funcionario '" + fc.getNome() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Funcionario '" + fc.getNome() + "' não pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listFuncionarios(HttpServletRequest req) {
		DAOFuncionarioCliente dao = DAOFactory.createDAO(DAOFuncionarioCliente.class);
		
		List<FuncionarioCliente> funcionarios = null;
		try {
			funcionarios = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (funcionarios != null)
			req.setAttribute("funcionarios", funcionarios);
	}
}
