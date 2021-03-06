package br.com.alura.leilao.leilao;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.alura.leilao.base.PageObject;

public class LeiloesPage extends PageObject {

	public LeiloesPage(WebDriver browser) {
		super(browser);
	}

	private static final String URL = "http://localhost:8080/leiloes";

	
	public boolean isPaginaAtual() {
		return this.browser.getCurrentUrl().equals(URL);
	}

	public CadastroLeiloesPage abrirFormularioDeNovoLeilao() {
		this.browser.findElement(By.id("novo_leilao_link")).click();
		return new CadastroLeiloesPage(this.browser);
	}

	public boolean isLeilaoCadastrado(String data, String nome, String valor) {

		// Navega para a ultima tr da tabela que tem o id table-leiloes
		WebElement ultimaLinhaDaTabela = this.browser.findElement(By.cssSelector("#table-leiloes tbody tr:last-child"));

		// Pegando o primeiro "filho" de cada linha
		WebElement colunaNome = ultimaLinhaDaTabela.findElement(By.cssSelector("td:nth-child(1)"));
		WebElement colunaDataAbertura = ultimaLinhaDaTabela.findElement(By.cssSelector("td:nth-child(2)"));
		WebElement colunaValorInicial = ultimaLinhaDaTabela.findElement(By.cssSelector("td:nth-child(3)"));

		return colunaNome.getText().equals(nome) && colunaDataAbertura.getText().equals(data)
				&& colunaValorInicial.getText().equals(valor);
	}

}
