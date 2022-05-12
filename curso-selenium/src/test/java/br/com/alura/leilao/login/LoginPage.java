package br.com.alura.leilao.login;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import br.com.alura.leilao.base.PageObject;
import br.com.alura.leilao.leilao.LeiloesPage;

public class LoginPage extends PageObject{

	private static final String URL = "http://localhost:8080/login";
	private static final String URL_ERRO = "http://localhost:8080/login?error";

	public LoginPage() {
		
		// Passando nulo para a classe mae criar um novo chrome driver
		super(null);
		this.browser.navigate().to(URL);
	}

	public LeiloesPage submitLoginForm(String username, String password) {

		// Achamos o textField do username e passamos a string
		browser.findElement(By.id("username")).sendKeys(username);

		browser.findElement(By.id("password")).sendKeys(password);

		// Achamos o form e submetemos
		browser.findElement(By.id("login-form")).submit();
		
		return new LeiloesPage(browser);
	}

	public boolean estaNaPaginaDeLogin() {
		return browser.getCurrentUrl().equals(URL);
	}

	public boolean estaNaPaginaDeErroDoLogin() {
		return browser.getCurrentUrl().equals(URL_ERRO);
	}

	public String getUsuarioLogado() {
		
		try {
			return browser.findElement(By.id("logged-username")).getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void navegaParaPaginaDeLances() {
		browser.navigate().to("http://localhost:8080/leiloes/2");
	}

	public boolean contem(String texto) {
		return browser.getPageSource().contains(texto);
	}

}
