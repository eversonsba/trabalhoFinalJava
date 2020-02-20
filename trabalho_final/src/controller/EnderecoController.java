package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import model.Endereco;
import model.ModelException;
import model.dao.DAOCliente;
import model.dao.DAOEnderecos;
import model.dao.DAOFactory;

@SuppressWarnings("serial")
// Herda da classe HttpServlet (extends HttpServlet) para ser tratada como um Servlet
// Anota a classe (@WebServlet) ajustando as URLs (urlPatterns) as quais ela responde
@WebServlet(urlPatterns = {"/enderecos", "/endereco/form", "/endereco/delete", "/endereco/insert", "/endereco/update"})
public class EnderecoController extends HttpServlet {
	
	// Sobrescreve o m√©todo doPost, sendo capaz de responder m√©todos HTTP POST
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/ProjetoFinalJava/endereco/form": {
			listEnderecos(req);
			listClientes(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-endereco.jsp");
			break;
		}
		case "/ProjetoFinalJava/endereco/update": {
			listClientes(req);
			Endereco e = loadEndereco(req);
			req.setAttribute("endereco", e);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-endereco.jsp");
			break;
		}
		default:
			listEnderecos(req);
			listClientes(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/enderecos.jsp");
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
		case "/ProjetoFinalJava/endereco/delete":
			deleteEndereco(req, resp);
			break;
		case "/ProjetoFinalJava/endereco/insert": {
			try {
				insertEndereco(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "/ProjetoFinalJava/endereco/update": {
			try {
				updateEndereco(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		default:
			System.out.println("URL inv·lida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/enderecos");
	}
	
	private Endereco loadEndereco(HttpServletRequest req) {
		String enderecoIdParameter = req.getParameter("enderecoId");
		
		int enderecoId = Integer.parseInt(enderecoIdParameter);
		
		DAOEnderecos dao = DAOFactory.createDAO(DAOEnderecos.class);
		
		try {
			Endereco en = dao.findByI(enderecoId);
			
			if (en == null)
				throw new ModelException("Funcion·rio n„o encontrado para alteraÁ„o");
			
			return en;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateEndereco(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String rua = req.getParameter("rua");
		String cidade = req.getParameter("cidade");
		String numeroParametro = req.getParameter("numero");
		int numero = Integer.parseInt(numeroParametro);
		String cnpjCliente = req.getParameter("cliente");
		
		Endereco en = loadEndereco(req);
		en.setCidade(cidade);
		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpjCliente);
		en.setCliente(c);
		en.setRua(rua);
		en.setNumero(numero);
		
		DAOEnderecos dao = DAOFactory.createDAO(DAOEnderecos.class);
		
		try {
			if (dao.update(en)) {
				ControllerUtil.sucessMessage(req, "Endereco '" + en.getRua() +" "+en.getNumero()  + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Endereco '" + en.getRua()+" "+en.getNumero()+" " + "' n„o pode ser atualizado.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertEndereco(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String rua = req.getParameter("rua");
		String cidade = req.getParameter("cidade");
		String numeroParametro = req.getParameter("numero");
		int numero = Integer.parseInt(numeroParametro);
		String cnpjCliente = req.getParameter("cliente");
		
		Endereco en = new Endereco(0);
		en.setCidade(cidade);
		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpjCliente);
		en.setCliente(c);
		en.setRua(rua);
		en.setNumero(numero);
		
		DAOEnderecos dao = DAOFactory.createDAO(DAOEnderecos.class);
		
		try {
			if (dao.save(en)) {
				ControllerUtil.sucessMessage(req, "Endereco '" + en.getRua() +" "+en.getNumero()  + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Endereco '" + en.getRua() +" "+en.getNumero()  + "' n„o pode ser salvo.");
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

	private void deleteEndereco(HttpServletRequest req, HttpServletResponse resp) {
		String enderecoIdParameter = req.getParameter("id");
		
		int enderecoId = Integer.parseInt(enderecoIdParameter);
		
		DAOEnderecos dao = DAOFactory.createDAO(DAOEnderecos.class);
		
		try {
			Endereco en = dao.findByI(enderecoId);
			
			if (en == null)
				throw new ModelException("Endereco n„o encontrado para deleÁ„o");
			
			if (dao.delete(en)) {
				ControllerUtil.sucessMessage(req, "Endereco '" + en.getRua() +" "+en.getNumero()  + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Endereco '" + en.getRua() +" "+en.getNumero()  + "' n√£o pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listEnderecos(HttpServletRequest req) {
		DAOEnderecos dao = DAOFactory.createDAO(DAOEnderecos.class);
		
		List<Endereco> enderecos = null;
		try {
			enderecos = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (enderecos != null)
			req.setAttribute("enderecos", enderecos);
	}
}
