package br.com.alverad.meu_bolsista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MeuBolsistaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuBolsistaApplication.class, args);
	}

}
