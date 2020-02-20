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
			<h3 class="page-header">${empty Complemento ? "Adicionar Complemento" : "Editar Complemento"}</h3>

			<form action="${pageContext.request.contextPath}/complemento/${action}" method="POST">
				<input type="hidden" value="${complemento.getId()}" name="id">
				<div class="row">
					<div class="form-group col-md-4">
					<label for="name">Descrição</label>
						<input type="text" class="form-control" id="descricao" name="descricao" 
							   autofocus="autofocus" placeholder="Descrição do Complemento" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a descrição do Complemento')"
							   oninput="setCustomValidity('')"
							   value="${complemento.getDescricao()}">
					</div>

							
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/complementos" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty complemento ? "Alterar Complemento" : "Cadastrar Complemento"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
