package br.com.pucminas.sigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SigoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SigoApplication.class, args);
  }

}
