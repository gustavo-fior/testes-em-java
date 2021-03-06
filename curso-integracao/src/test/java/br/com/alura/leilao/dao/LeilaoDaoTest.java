package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LeilaoDaoTest {

	private LeilaoDao leilaoDao;

	private EntityManager em;

	@BeforeEach
	void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.leilaoDao = new LeilaoDao(em);
		em.getTransaction().begin();
	}

	@AfterEach
	void afterEach() {

		// Desfaz todas as alteracoes de cada teste
		em.getTransaction().rollback();
	}

	@Test
	void deveriaCadastrarLeilao() {

		Usuario fulano = new UsuarioBuilder(em).comNome("fulano").comEmail("fulano@email.com").comSenha("12345").salvarNoBanco().build();
		
		Leilao leilaoSalvo = criaLeilaoCelular(fulano);

		assertNotNull(leilaoSalvo);

	}

	@Test
	void deveriaAtualizarLeilao() {

		Usuario fulano = new UsuarioBuilder(em).comNome("fulano").comEmail("fulano@email.com").comSenha("12345").salvarNoBanco().build();

		Leilao leilaoSalvo = criaLeilaoCelular(fulano);

		leilaoSalvo.setNome("iPhone");
		leilaoSalvo.setValorInicial(new BigDecimal("10000"));

		leilaoDao.salvar(leilaoSalvo);
		Leilao leilaoModificado = leilaoDao.buscarPorId(leilaoSalvo.getId());

		assertEquals("iPhone", leilaoModificado.getNome());
		assertEquals(new BigDecimal("10000"), leilaoModificado.getValorInicial());

	}

	private Leilao criaLeilaoCelular(Usuario usuario) {

		Leilao leilaoSalvo = new Leilao("celular", new BigDecimal("500"), LocalDate.now(), usuario);

		em.persist(leilaoSalvo);
		
		return leilaoSalvo;

	}

}
