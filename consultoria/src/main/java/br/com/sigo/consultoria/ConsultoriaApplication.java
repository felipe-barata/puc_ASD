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
}
