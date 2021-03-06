package br.com.alura.leilao.builder;

import javax.persistence.EntityManager;

import br.com.alura.leilao.model.Usuario;

public class UsuarioBuilder {

	private String nome;
	private String email;
	private String senha;
	private boolean salvar = false;
	private EntityManager em;
	
	public UsuarioBuilder() {
	}

	public UsuarioBuilder(EntityManager em) {
		this.em = em;
	}

	public UsuarioBuilder comNome(String nome) {
		this.nome = nome;
		return this;
	}

	public UsuarioBuilder comEmail(String email) {
		this.email = email;
		return this;
	}

	public UsuarioBuilder comSenha(String senha) {
		this.senha = senha;
		return this;
	}

	public Usuario build() {

		Usuario usuario = new Usuario(this.nome, this.email, this.senha);

		if (salvar) {
			this.em.persist(usuario);
		}
		return usuario;
	}

	public UsuarioBuilder salvarNoBanco() {
		this.salvar = true;
		return this;
	}

}
