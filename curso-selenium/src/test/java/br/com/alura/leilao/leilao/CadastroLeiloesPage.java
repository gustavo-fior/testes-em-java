package br.com.alura.leilao.leilao;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.alura.leilao.base.PageObject;

public class CadastroLeiloesPage extends PageObject {

	private static final String URL = "http://localhost:8080/leiloes/new";

	// Compartilhando o mesmo browser para nao abrir uma nova janela apos o login
	public CadastroLeiloesPage(WebDriver browser) {
		super(browser);
	}

	public LeiloesPage cadastrarNovoLeilao(String data, String nome, String valor) {

		this.browser.findElement(By.id("nome")).sendKeys(nome);
		this.browser.findElement(By.id("valorInicial")).sendKeys(valor);
		this.browser.findElement(By.id("dataAbertura")).sendKeys(data);

		this.browser.findElement(By.id("button-submit")).click();

		return new LeiloesPage(this.browser);
	}

	public boolean isPaginaAtual() {
		return this.browser.getCurrentUrl().equals(URL);
	}

	public boolean isValidacoesVisiveis() {

		String codigoFonte = this.browser.getPageSource();

		return codigoFonte.contains("não deve estar em branco") && codigoFonte.contains("minimo 3 caracteres")
				&& codigoFonte.contains("deve ser um valor maior de 0.1")
				&& codigoFonte.contains("deve ser uma data no formato dd/MM/yyyy");
	}

}
