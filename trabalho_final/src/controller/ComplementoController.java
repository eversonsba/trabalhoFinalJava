package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Complemento;
import model.ModelException;
import model.dao.DAOComplemento;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/complementos", "/complemento/form", "/complemento/delete", "/complemento/insert", "/complemento/update"})
public class ComplementoController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getRequestURI();
		
		switch (action) {
		case "/ProjetoFinalJava/complemento/form": {
			listComplementos(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-complemento.jsp");
			break;
		}
		case "/ProjetoFinalJava/complemento/update": {
			listComplementos(req);
			Complemento complemento = loadComplemento(req);
			req.setAttribute("complemento", complemento);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-complemento.jsp");
			break;
		}
		default:
			listComplementos(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/complementos.jsp");
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
		case "/ProjetoFinalJava/complemento/delete":
			deleteComplemento(req, resp);
			break;	
		case "/ProjetoFinalJava/complemento/insert": {
			insertComplemento(req, resp);
			break;
		}
		case "/ProjetoFinalJava/complemento/update": {
			updateComplemento(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
			break;
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/complementos");
	}

	private Complemento loadComplemento(HttpServletRequest req) {
		String complementoIdOarameter = req.getParameter("id");
		int idComplemento = Integer.parseInt(complementoIdOarameter);	
		DAOComplemento dao = DAOFactory.createDAO(DAOComplemento.class);
		
		try {
			Complemento complemento = dao.findById(idComplemento);
			
			if (complemento == null)
				throw new ModelException("Complemento não encontrado para alteração");
			
			return complemento;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
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
	
	private void insertComplemento(HttpServletRequest req, HttpServletResponse resp) {
		String descricao = req.getParameter("descricao");
		
		Complemento complemento = new Complemento(0);
		
		complemento.setDescricao(descricao);
		
		DAOComplemento dao = DAOFactory.createDAO(DAOComplemento.class);
		
		try {
			if (dao.save(complemento)) {
				ControllerUtil.sucessMessage(req, "Complemento '" + complemento.getDescricao() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Complemento '" + complemento.getDescricao() + "' nÃ£o pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void updateComplemento(HttpServletRequest req, HttpServletResponse resp) {
		String idComplementoParametro = req.getParameter("idComplemento");
		int idComplemento = Integer.parseInt(idComplementoParametro);
		String descricao = req.getParameter("descricao");
		
		Complemento c = new Complemento(idComplemento);
		c.setDescricao(descricao);
			
		DAOComplemento dao = DAOFactory.createDAO(DAOComplemento.class);
		
		try {
			if (dao.update(c)) {
				ControllerUtil.sucessMessage(req, "Complemento '" + c.getDescricao() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Complemento '" + c.getDescricao() + "' não pode ser atualizado.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void deleteComplemento(HttpServletRequest req, HttpServletResponse resp) {
		String complementoParametro = req.getParameter("id");
		int idComplemento = Integer.parseInt(complementoParametro);
		DAOComplemento dao = DAOFactory.createDAO(DAOComplemento.class);
		
		try {
			Complemento c = dao.findById(idComplemento);
			
			if (c == null)
				throw new ModelException("Complemento não encontrado para deleção.");
			
			if (dao.delete(c)) {
				ControllerUtil.sucessMessage(req, "Complemento '" + c.getDescricao() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Complemento '" + c.getDescricao()+ "' não pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
