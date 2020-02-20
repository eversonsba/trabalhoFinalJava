<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="WEB-INF/base-head.jsp"%>
	</head>
	<body>
		<%@include file="WEB-INF/nav-menu.jsp"%>
			
		<div id="container" class="container-fluid">
			<h3 class="page-header">${empty servico ? "Adicionar Servico" : "Editar Servico"}</h3>

			<form action="${pageContext.request.contextPath}/servico/${action}" method="POST">
				<input type="hidden" value="${servico.getId()}" name="servicoId">
				<div class="row">
					<div class="form-group col-md-6">
					<label for="content">Descricao</label>
						<input type="text" class="form-control" id="descricao" name="descricao" 
							   autofocus="autofocus" placeholder="Descricao do servico" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a descricao do servico')"
							   oninput="setCustomValidity('')"
							   value="${servico.getDescricao()}">
					</div>
					
					<div class="form-group col-md-6">
					<label for="content">Valor</label>
						<input type="text" class="form-control" id="valor" name="valor" 
							   autofocus="autofocus" placeholder="Valor do serviço" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o valor do serviço')"
							   oninput="setCustomValidity('')"
							   value="${servico.getValor()}">
					</div>
					
					<div class="form-group col-md-6">
					<label for="content">Data</label>
						<input type="date" class="form-control" id="data" name="data" 
							   autofocus="autofocus" placeholder="Data do servico" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a data do serviço')"
							   oninput="setCustomValidity('')"
							   value="${servico.getData()}">
					</div>

					<div class="form-group col-md-6">
						<label for="cliente">Cliente</label>
						<select id="nome" class="form-control selectpicker" name="cliente" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o Cliente.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty cliente ? "" : "selected"}>Selecione um Cliente</option>
						  <c:forEach var="cliente" items="${clientes}">
						  	<option value="${cliente.getCnpj()}"  ${servico.getCliente().getCnpj() == cliente.getCnpj() ? "selected" : ""}>
						  		${cliente.getNome()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
					
					<div class="form-group col-md-6">
						<label for="clinnte">Funcionario</label>
						<select id="funcionario" class="form-control selectpicker" name="funcionario" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o Funcionario.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um Funcionario</option>
						  <c:forEach var="funcionario" items="${funcionarios}">
						  	<option value="${funcionario.getId()}"  ${servico.getFuncionarioCliente().getId() == funcionario.getId() ? "selected" : ""}>
						  		${funcionario.getNome()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
					
					<div class="form-group col-md-6">
						<label for="clinnte">Complemento</label>
						<select id="nome" class="form-control selectpicker" name="complemento" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o Complemento')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um Complemento</option>
						  <c:forEach var="complemento" items="${complementos}">
						  	<option value="${complemento.getId()}"  ${servico.getComplemento().getId() == complemento.getId() ? "selected" : ""}>
						  		${complemento.getDescricao()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>					
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty servico ? "Alterar Servico" : "Criar Servico"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
