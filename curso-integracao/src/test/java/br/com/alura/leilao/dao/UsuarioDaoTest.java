package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.builder.UsuarioBuilder;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private UsuarioDao usuarioDao;

	private EntityManager em;

	@BeforeEach
	void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.usuarioDao = new UsuarioDao(em);

		em.getTransaction().begin();
	}

	@AfterEach
	void afterEach() {

		// Desfaz todas as alteracoes de cada teste
		em.getTransaction().rollback();
	}

	@Test
	void deveriaEncontrarUsuarioCadastrado() {

		Usuario fulano = new UsuarioBuilder(em).comNome("fulano").comEmail("fulano@email.com").comSenha("12345").salvarNoBanco().build();

		Usuario usuarioBuscado = this.usuarioDao.buscarPorUsername(fulano.getNome());

		assertNotNull(usuarioBuscado);

	}

	@Test
	void deveriaNaoEncontrarUsuarioNaoCadastrado() {
		assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername("beltrano"));
	}

	@Test
	void deveriaDeletarUsuario() {

		Usuario fulano = new UsuarioBuilder(em).comNome("fulano").comEmail("fulano@email.com").comSenha("12345").salvarNoBanco().build();

		usuarioDao.deletar(fulano);

		assertThrows(NoResultException.class, () -> usuarioDao.buscarPorUsername(fulano.getNome()));

	}

}
