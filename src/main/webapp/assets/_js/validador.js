/**
 * 
 */

function validarNovoFornecedor() {
	
	let nome = frmFornecedor.nome.value;
	let tipo = frmFornecedor.tipo.value;
	
	if (nome === "") {
		alert("Preencha o campo nome!");
		frmFornecedor.nome.focus();
		return false;
	}
	
	if (tipo === "") {
		alert("Preencha o campo tipo!");
		frmFornecedor.tipo.focus();
		return false;
	}
	
	document.forms["frmFornecedor"].submit();
	
}

function validarNovoUsuario() {
	
	let nome = frmUsuario.nome.value;
	let email = frmUsuario.email.value;
	let papel = frmUsuario.email.value;
	let senha = frmUsuario.senha.value;
	let repetirSenha = frmUsuario.repetirSenha.value;
	
	if (nome === "") {
		alert("Preencha o campo nome!");
		frmUsuario.nome.focus();
		return false;
	}
	
	if (email === "") {
		alert("Preencha o campo e-mail!");
		frmUsuario.email.focus();
		return false;
	}
	
	if (papel === "") {
		alert("Preencha o campo papel!");
		frmUsuario.email.focus();
		return false;
	}
	
	if (senha === "") {
		alert("Preencha o campo senha!");
		frmUsuario.senha.focus();
		return false;
	}
	
	if (repetirSenha === "") {
		alert("Preencha o campo repetir senha!");
		frmUsuario.repetirSenha.focus();
		return false;
	}
	
	if (senha !== repetirSenha) {
		alert("As senhas não são compatíveis!");
		frmUsuario.repetirSenha.focus();
		return false;
	}
	
	document.forms["frmUsuario"].submit();
	
}















