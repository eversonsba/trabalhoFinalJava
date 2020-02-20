package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import model.Complemento;
import model.FuncionarioCliente;
import model.ModelException;
import model.Servico;
import model.dao.DAOCliente;
import model.dao.DAOComplemento;
import model.dao.DAOFactory;
import model.dao.DAOFuncionarioCliente;
import model.dao.DAOServico;

@SuppressWarnings("serial")
// Herda da classe HttpServlet (extends HttpServlet) para ser tratada como um Servlet
// Anota a classe (@WebServlet) ajustando as URLs (urlPatterns) as quais ela responde
@WebServlet(urlPatterns = {"", "/servicos", "/servico/form", "/servico/delete", "/servico/insert", "/servico/update"})
public class servicoController extends HttpServlet {
	
	// Sobrescreve o m√©todo doPost, sendo capaz de responder m√©todos HTTP POST
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/ProjetoFinalJava/servico/form": {
			listServicos(req);
			listClientes(req);
			listFuncionarios(req);
			listComplementos(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-servico.jsp");
			break;
		}
		case "/ProjetoFinalJava/servico/update": {
			listServicos(req);
			listClientes(req);
			listComplementos(req);
			listFuncionarios(req);
			Servico s = loadServico(req);
			req.setAttribute("servico", s);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-servico.jsp");
			break;
		}
		default:
			listServicos(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/servicos.jsp");
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
		case "/ProjetoFinalJava/servico/delete":
			deleteServico(req, resp);
			break;
		case "/ProjetoFinalJava/servico/insert": {
			try {
				insertServico(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case "/ProjetoFinalJava/servico/update": {
			try {
				updateServico(req, resp);
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		default:
			System.out.println("URL inv√°lida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/servicos");
	}
	
	private Servico loadServico(HttpServletRequest req) {
		String servicoIdParameter = req.getParameter("servicoId");
		
		int servicoId = Integer.parseInt(servicoIdParameter);
		
		DAOServico dao = DAOFactory.createDAO(DAOServico.class);
		
		try {
			Servico s = dao.findById(servicoId);
			
			if (s == null)
				throw new ModelException("Servico n„o encontrado para alteraÁ„o");
			
			return s;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}

	private void updateServico(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String descricao = req.getParameter("descricao");
		String valorParametro = req.getParameter("valor");
		Double valor = Double.parseDouble(valorParametro);	
		Servico s = loadServico(req);
		
		s.setValor(valor);
		s.setDescricao(descricao);
		
		DAOServico dao = DAOFactory.createDAO(DAOServico.class);
		
		try {
			if (dao.update(s)) {
				ControllerUtil.sucessMessage(req, "Servico '" + s.getDescricao() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Servico '" + s.getDescricao() + "' n„o pode ser atualizado.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertServico(HttpServletRequest req, HttpServletResponse resp) throws ModelException {
		String descricao = req.getParameter("descricao");
		String valorParametro = req.getParameter("valor");
		double valor = Double.parseDouble(valorParametro);
		
		String cnpj_cliente = req.getParameter("cliente");
		
		String idComplementoParametro =req.getParameter("complemento");
		int idComplemento = Integer.parseInt(idComplementoParametro);
		
		String idFuncionarioParametro = req.getParameter("funcionario");
		int idFuncionario = Integer.parseInt(idFuncionarioParametro);
		
		
		
		Servico s = new Servico(0);
		
		s.setValor(valor);
		s.setDescricao(descricao);

		DAOCliente daoc = DAOFactory.createDAO(DAOCliente.class);
		Cliente c = daoc.findByCNPJ(cnpj_cliente);
		s.setCliente(c);
		
		DAOComplemento daoco = DAOFactory.createDAO(DAOComplemento.class);
		Complemento co = daoco.findById(idComplemento);
		
		s.setComplemento(co);
		
		DAOFuncionarioCliente daof = DAOFactory.createDAO(DAOFuncionarioCliente.class);
		FuncionarioCliente fc = daof.findByI(idFuncionario);
		
		s.setFuncionarioCliente(fc);
		
		DAOServico dao = DAOFactory.createDAO(DAOServico.class);
		
		try {
			if (dao.save(s)) {
				ControllerUtil.sucessMessage(req, "Servico '" + s.getDescricao() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Servico '" + s.getDescricao() + "' n„o pode ser salvo.");
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

	private void deleteServico(HttpServletRequest req, HttpServletResponse resp) {
		String servicoParameter = req.getParameter("id");
		
		int servicoId = Integer.parseInt(servicoParameter);
		
		DAOServico dao = DAOFactory.createDAO(DAOServico.class);
		
		try {
			Servico s = dao.findById(servicoId);
			
			if (s == null)
				throw new ModelException("Servico n„o encontrado para deleÁ„o");
			
			if (dao.delete(s)) {
				ControllerUtil.sucessMessage(req, "Servico '" + s.getDescricao() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Servico '" + s.getDescricao() + "' n„o pode ser deletado.");
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
	
	private void listServicos(HttpServletRequest req) {
		DAOServico dao = DAOFactory.createDAO(DAOServico.class);
		
		List<Servico> servicos = null;
		try {
			servicos = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (servicos != null)
			req.setAttribute("servicos", servicos);
	}
	private void listComplementos(HttpServletRequest req) {
		DAOComplemento dao = DAOFactory.createDAO(DAOComplemento.class);
		
		List<Complemento> complementos = null;
		try {
			complementos = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (complementos != null)
			req.setAttribute("complementos", complementos);
	}
}
