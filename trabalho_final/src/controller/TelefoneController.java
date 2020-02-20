package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import model.Telefone;
import model.ModelException;
import model.dao.DAOCliente;
import model.dao.DAOTelefone;
import model.dao.DAOFactory;

@SuppressWarnings("serial")
// Herda da classe HttpServlet (extends HttpServlet) para ser tratada como um Servlet
// Anota a classe (@WebServlet) ajustando as URLs (urlPatterns) as quais ela responde
@WebServlet(urlPatterns = {"/telefones", "/telefone/form", "/telefone/delete", "/telefone/insert", "/telefone/update"})
public class TelefoneController extends HttpServlet {
	
	// Sobrescreve o mÃ©todo doPost, sendo capaz de responder mÃ©todos HTTP POST
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/ProjetoFinalJava/telefone/form": {
			listTelefones(req);
			listClientes(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-telefone.jsp");
			break;
		}
		case "/ProjetoFinalJava/telefone/update": {
			listClientes(req);
			Telefone t = loadTelefone(req);
			req.setAttribute("telefone", t);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-telefone.jsp");
			break;
		}
		default:
			listTelefones(req);
			listClientes(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/telefones.jsp");
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
		case "/ProjetoFinalJava/telefone/delete":
			deleteTelefone(req, resp);
			break;
		case "/ProjetoFinalJava/telefone/insert": {
			try {
				insertTelefone(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "/ProjetoFinalJava/telefone/update": {
			try {
				updateTelefone(req, resp);
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
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/telefones");
	}
	
	private Telefone loadTelefone(HttpServletRequest req) {
		String telefoneIdParameter = req.getParameter("telefoneId");
		
		int telefoneId = Integer.parseInt(telefoneIdParameter);
		
		DAOTelefone dao = DAOFactory.createDAO(DAOTelefone.class);
		
		try {
			Telefone t = dao.findByI(telefoneId);
			
			if (t == null)
				throw new ModelException("Telefone não encontrado para alteração");
			
			return t;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateTelefone(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String numero = req.getParameter("numero");
		String cnpj = req.getParameter("cliente");
		
		Telefone t = loadTelefone(req);
		t.setNumero(numero);;
		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpj);
		t.setCliente(c);
		
		DAOTelefone dao = DAOFactory.createDAO(DAOTelefone.class);
		
		try {
			if (dao.update(t)) {
				ControllerUtil.sucessMessage(req, "Telefone '" + t.getNumero() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Telefone '" + t.getNumero()+ "' não pode ser atualizado.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertTelefone(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String numero = req.getParameter("numero");
		String cnpj = req.getParameter("cliente");
		
		Telefone t = new Telefone(0);
		t.setNumero(numero);;
		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpj);
		t.setCliente(c);
		
		DAOTelefone dao = DAOFactory.createDAO(DAOTelefone.class);
		
		try {
			if (dao.save(t)) {
				ControllerUtil.sucessMessage(req, "Telefone '" + t.getNumero()  + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "telefone '" + t.getNumero()+ "' não pode ser salvo.");
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

	private void deleteTelefone(HttpServletRequest req, HttpServletResponse resp) {
		String telefoneIdParameter = req.getParameter("id");
		
		int telefoneId = Integer.parseInt(telefoneIdParameter);
		
		DAOTelefone dao = DAOFactory.createDAO(DAOTelefone.class);
		
		try {
			Telefone t = dao.findByI(telefoneId);
			
			if (t == null)
				throw new ModelException("Telefone não encontrado para deleção");
			
			if (dao.delete(t)) {
				ControllerUtil.sucessMessage(req, "Telefone '" +t.getNumero()  + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Telefone '" + t.getNumero()  + "' não pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listTelefones(HttpServletRequest req) {
		DAOTelefone dao = DAOFactory.createDAO(DAOTelefone.class);
		
		List<Telefone> telefones = null;
		try {
			telefones = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (telefones != null)
			req.setAttribute("telefones", telefones);
	}
}
