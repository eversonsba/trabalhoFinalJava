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
			<h3 class="page-header">${empty funcionario ? "Adicionar Funcionario" : "Editar Funcionario"}</h3>

			<form action="${pageContext.request.contextPath}/funcionario/${action}" method="POST">
				<input type="hidden" value="${funcionario.getId()}" name="funcionarioId">
				<div class="row">
					<div class="form-group col-md-6">
					<label for="content">Nome</label>
						<input type="text" class="form-control" id="nome" name="nome" 
							   autofocus="autofocus" placeholder="Nome do Funcionario" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome do Funcionário.)"
							   oninput="setCustomValidity('')"
							   value="${funcionario.getNome()}">
					</div>

					<div class="form-group col-md-6">
						<label for="clinnte">Cliente</label>
						<select id="nome" class="form-control selectpicker" name="cliente" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o Cliente.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um Cliente</option>
						  <c:forEach var="cliente" items="${clientes}">
						  	<option value="${cliente.getCnpj()}"  ${funcionario.getCliente().getCnpj() == cliente.getCnpj() ? "selected" : ""}>
						  		${cliente.getNome()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/funcionarios" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty funcionario ? "Alterar Funcionario" : "Criar Funcionario"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
