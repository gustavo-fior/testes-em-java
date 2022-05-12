package br.com.alura.leilao.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PageObject {

	// Para as classes filhas tambem poderem acessar
	protected WebDriver browser;

	// Compartilhando o mesmo browser para nao abrir uma nova janela apos o login
	public PageObject(WebDriver browser) {

		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");

		if (browser == null) {
			this.browser = new ChromeDriver();
		} else {
			this.browser = browser;
		}
		
		// Tempo de espera enquanto o selenium pega um elemento na pagina 
		// Tempo de espera que o selenium ira aguardar o carregamento de uma pagina
		// Alguns codigos javascript e ajax podem dar erro nos testes
		this.browser.manage().timeouts()
		.implicitlyWait(5, TimeUnit.SECONDS)
		.pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	public void fechar() {
		this.browser.quit();
	}

}
