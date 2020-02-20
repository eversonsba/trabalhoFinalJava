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
			<h3 class="page-header">${empty cliente ? "Adicionar Cliente" : "Editar Cliente"}</h3>

			<form action="${pageContext.request.contextPath}/cliente/${action}" method="POST">
				<input type="hidden" value="${cliente.getCnpj()}" name="id">
				<div class="row">
					<div class="form-group col-md-4">
					<label for="name">Nome</label>
						<input type="text" class="form-control" id="nome" name="nome" 
							   autofocus="autofocus" placeholder="Nome do Cliente" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome do Cliente')"
							   oninput="setCustomValidity('')"
							   value="${cliente.getNome()}">
					</div>

					<div class="form-group col-md-4">
						<label for="name">Cnpj</label>
						<input type="text" class="form-control" id="cnpj" name="cnpj" 
							   autofocus="autofocus" placeholder="cnpj do Cliente" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o cnpj do Cliente.')"
							   oninput="setCustomValidity('')"
							   value="${cliente.getCnpj()}">
					</div>
					</div>
					
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/clientes" class="btn btn-default">Cancelar</a>
						<button type="submit"x class="btn btn-primary">${not empty cliente ? "Alterar Cliente" : "Cadastrar Cliente"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
