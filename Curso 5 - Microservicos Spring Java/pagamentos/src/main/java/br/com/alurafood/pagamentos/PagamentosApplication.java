package br.com.alurafood.pagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //Essa anotação serve para ele saber que é um cliente e vai se registrar em algum Eureka.
 // Eureka adicionado na pom na versao : org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.2
public class PagamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentosApplication.class, args);
	}

}
