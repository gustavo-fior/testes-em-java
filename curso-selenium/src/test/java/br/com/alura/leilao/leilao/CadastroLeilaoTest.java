package br.com.alura.leilao.leilao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.login.LoginPage;

public class CadastroLeilaoTest {

	private LeiloesPage paginaDeLeiloes;
	private CadastroLeiloesPage paginaDeCadastroDeLeilao;
	
	@BeforeEach
	void beforeEach() {
		
		LoginPage paginaDeLogin = new LoginPage();

		// Modificamos o metodo para retornar a pagina que eh carregada depois do login
		// ter sido feito corretamente (de leiloes)
		this.paginaDeLeiloes = paginaDeLogin.submitLoginForm("fulano", "pass");

		this.paginaDeCadastroDeLeilao = paginaDeLeiloes.abrirFormularioDeNovoLeilao();

		
	}

	@AfterEach
	void afterEach() {
		this.paginaDeLeiloes.fechar();
	}

	@Test
	void deveriaCadastrarLeilaoCorretamente() {
		
		// Variaveis para criacao do leilao
		String hoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String nome = "Leilao do dia " + hoje;
		String valor = "500.00";

		this.paginaDeLeiloes = paginaDeCadastroDeLeilao.cadastrarNovoLeilao(hoje, nome, valor);

		assertTrue(paginaDeLeiloes.isLeilaoCadastrado(hoje, nome, valor));

	}

	@Test
	void deveriaValidarCadastroDeLeilao() {

		this.paginaDeLeiloes = this.paginaDeCadastroDeLeilao.cadastrarNovoLeilao("", "", "");

		assertFalse(this.paginaDeCadastroDeLeilao.isPaginaAtual());
		assertTrue(this.paginaDeLeiloes.isPaginaAtual());
		assertTrue(this.paginaDeCadastroDeLeilao.isValidacoesVisiveis());
		
	}

}
