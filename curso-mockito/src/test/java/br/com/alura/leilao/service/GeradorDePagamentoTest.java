package br.com.alura.leilao.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Pagamento;
import br.com.alura.leilao.model.Usuario;

class GeradorDePagamentoTest {

	private GeradorDePagamento geradorDePagamento;

	@Mock
	private PagamentoDao pagamentoDao;

	// Mockito recupera objetos do tipo pagamento
	@Captor
	private ArgumentCaptor<Pagamento> captor;

	// Para mockarmos um relogio e nao usarmos o LocalDate.now()
	@Mock
	private Clock clock;

	@BeforeEach
	void beforeEach() {

		MockitoAnnotations.initMocks(this);

		this.geradorDePagamento = new GeradorDePagamento(pagamentoDao, clock);

	}

	@Test
	void deveriaCriarPagamentoParaVencedorDoLeilao() {

		Lance lanceVencedor = getLeiloes().get(0).getLances().get(0);

		LocalDate dataFixa = LocalDate.of(2020, 12, 7);
		Instant instante = dataFixa.atStartOfDay(ZoneId.systemDefault()).toInstant();

		// Quando o instante do relogio for chamado, retornar o instante da data fixa criada acima
		Mockito.when(clock.instant()).thenReturn(instante);
		Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

		geradorDePagamento.gerarPagamento(lanceVencedor);

		Mockito.verify(pagamentoDao).salvar(captor.capture());

		Pagamento pagamento = captor.getValue();

		assertEquals(LocalDate.now(clock).plusDays(1), pagamento.getVencimento());
		assertEquals(lanceVencedor.getValor(), pagamento.getValor());
		assertFalse(pagamento.getPago());
		assertEquals(lanceVencedor.getUsuario(), pagamento.getUsuario());
		assertEquals(lanceVencedor.getLeilao(), pagamento.getLeilao());

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
