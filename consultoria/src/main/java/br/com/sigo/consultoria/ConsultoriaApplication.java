package br.com.sigo.consultoria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ConsultoriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultoriaApplication.class, args);
	}


	///Segurança - O sistema deve apresentar altos padrões de segurança.
	//Implantação – O sistema deve utilizar práticas de integração contínua.
//Testabilidade - O sistema deve ser simples para testar.
	//Desempenho - O sistema deve ser rápido.
}
