package br.com.alura.leilao.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

	private LoginPage paginaDeLogin;

	@BeforeEach
	void beforeEach() {
		this.paginaDeLogin = new LoginPage();
	}

	@AfterEach
	void afterEach() {
		this.paginaDeLogin.fechar();
	}

	@Test
	void deveriaLogarUmUsuarioCorretamente() {

		paginaDeLogin.submitLoginForm("fulano", "pass");

		assertFalse(paginaDeLogin.estaNaPaginaDeLogin());
		assertEquals("fulano", paginaDeLogin.getUsuarioLogado());
	}

	@Test
	void naoDeveriaLogarUmUsuarioInvalido() {

		paginaDeLogin.submitLoginForm("invalido", "invalida");

		assertTrue(paginaDeLogin.estaNaPaginaDeErroDoLogin());
		assertNull(paginaDeLogin.getUsuarioLogado());
		assertTrue(paginaDeLogin.contem("Usuário e senha inválidos."));
	}

	@Test
	void naoDeveriaAcessarPaginaRestritaSemEstarLogado() {

		paginaDeLogin.navegaParaPaginaDeLances();

		assertTrue(paginaDeLogin.estaNaPaginaDeLogin());
		assertFalse(paginaDeLogin.contem("Dados do Leilão"));
	}
}
