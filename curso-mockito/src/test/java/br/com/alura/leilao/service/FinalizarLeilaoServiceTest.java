package br.com.alura.leilao.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.alura.leilao.dao.LeilaoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

class FinalizarLeilaoServiceTest {

	@Mock
	private LeilaoDao leilaoDao;

	@Mock
	private EnviadorDeEmails enviadorDeEmails;

	// Classe que vamos testar
	private FinalizarLeilaoService finalizarLeilaoService;

	@BeforeEach
	void beforeEach() {

		// Inicializa os mocks dessa classe de test
		MockitoAnnotations.initMocks(this);

		// Instanciando classe que vamos testar
		this.finalizarLeilaoService = new FinalizarLeilaoService(leilaoDao, enviadorDeEmails);
	}

	@Test
	void deveriaFinalizarCorretamenteUmLeilao() {

		List<Leilao> leiloes = getLeiloes();

		// Quando x metodo for chamado, retornar y
		Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(leiloes);

		finalizarLeilaoService.finalizarLeiloesExpirados();

		Leilao leilao = leiloes.get(0);

		assertTrue(leilao.isFechado());
		assertEquals(new BigDecimal("6000"), leilao.getLanceVencedor().getValor());

		// verifica se metodo salvar foi chamado
		Mockito.verify(leilaoDao).salvar(leilao);
	}

	@Test
	void deveriaEnviarEmailParaVencedorDoLeilao() {

		List<Leilao> leiloes = getLeiloes();

		// Quando x metodo for chamado, retornar y
		Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(leiloes);

		finalizarLeilaoService.finalizarLeiloesExpirados();

		Lance lanceVencedor = leiloes.get(0).getLanceVencedor();

		// verifica se metodo enviarEmailVencedorLeilao foi chamado
		Mockito.verify(enviadorDeEmails).enviarEmailVencedorLeilao(lanceVencedor);
	}

	@Test
	void naoDeveriaEnviarEmailParaVencedorDoLeilaoSeErroAcontecerAoEncerrarOLeilao() {

		List<Leilao> leiloes = getLeiloes();

		// Quando x metodo for chamado, retornar y
		Mockito.when(leilaoDao.buscarLeiloesExpirados()).thenReturn(leiloes);

		// Quando x metodo for chamado, lancar excecao y
		Mockito.when(leilaoDao.salvar(Mockito.any())).thenThrow(RuntimeException.class);
		
		try {
			
			finalizarLeilaoService.finalizarLeiloesExpirados();
			
			// verifica se nenhum metodo do Mock de enivadorDeEmail foi chamado
			Mockito.verifyNoInteractions(enviadorDeEmails);
			
		} catch (Exception e) {}


	}

	private List<Leilao> getLeiloes() {

		ArrayList<Leilao> leiloes = new ArrayList<Leilao>();

		Leilao leilao = new Leilao("iPhone", new BigDecimal("5000"), new Usuario("Gustavo"));

		Lance lance = new Lance(new Usuario("Lucas"), new BigDecimal("5500"));
		Lance lance2 = new Lance(new Usuario("Rafael"), new BigDecimal("6000"));

		leilao.propoe(lance);
		leilao.propoe(lance2);

		leiloes.add(leilao);

		return leiloes;
	}

}
