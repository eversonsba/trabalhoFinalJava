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
			<h3 class="page-header">${empty endereco ? "Adicionar Endereço" : "Editar Endereço"}</h3>

			<form action="${pageContext.request.contextPath}/endereco/${action}" method="POST">
				<input type="hidden" value="${endereco.getId()}" name="enderecoId">
				<div class="row">
					<div class="form-group col-md-6">
					<label for="content">Rua</label>
						<input type="text" class="form-control" id="rua" name="rua" 
							   autofocus="autofocus" placeholder="Rua" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a Rua.')"
							   oninput="setCustomValidity('')"
							   value="${endereco.getRua()}">
					</div>
					
					<div class="form-group col-md-6">
					<label for="content">Numero</label>
						<input type="text" class="form-control" id="numero" name="numero" 
							   autofocus="autofocus" placeholder="Numero da casa" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o numero da cas.')"
							   oninput="setCustomValidity('')"
							   value="${endereco.getNumero()}">
					</div>
					
					<div class="form-group col-md-6">
					<label for="content">Cidade</label>
						<input type="text" class="form-control" id="cidade" name="cidade" 
							   autofocus="autofocus" placeholder="Cidade" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a cidade.')"
							   oninput="setCustomValidity('')"
							   value="${endereco.getCidade()}">
					</div>

					<div class="form-group col-md-6">
						<label for="clinnte">Cliente</label>
						<select id="nome" class="form-control selectpicker" name="cliente" 
							    required oninvalid="this.setCustomValidity('Por favor, informe o Cliente.')"
							    oninput="setCustomValidity('')">
						  <option value="" disabled ${not empty post ? "" : "selected"}>Selecione um Cliente</option>
						  <c:forEach var="cliente" items="${clientes}">
						  	<option value="${cliente.getCnpj()}"  ${endereco.getCliente().getCnpj() == cliente.getCnpj() ? "selected" : ""}>
						  		${cliente.getNome()}
						  	</option>	
						  </c:forEach>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/enderecos" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty endereco ? "Alterar Endereco" : "Criar Endereco"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
