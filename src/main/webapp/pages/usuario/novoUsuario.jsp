<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Novo Usuário</title>
</head>
<body>

	<h1>Novo Usuário</h1>
	
	<form action="../UsuarioController" method="get">
	
		<label for="nome">Nome: </label>
		<input type="text" id="nome" name="nome" required>
		
		<button type="submit">Cadastrar</button>	
	</form>

</body>
</html>