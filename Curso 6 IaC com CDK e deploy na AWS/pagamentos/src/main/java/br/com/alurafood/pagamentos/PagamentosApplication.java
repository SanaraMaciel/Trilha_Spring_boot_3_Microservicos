package br.com.alurafood.pagamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//Essa anotação serve para ele saber que é um cliente e vai se registrar em algum Eureka.
// Eureka adicionado na pom na versao : org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.1.2
@EnableEurekaClient
//anotacao para que esse microservico de pagamento faca a integracao com o microservido de pedido no momento da confirmacao do pedido
@EnableFeignClients
public class PagamentosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagamentosApplication.class, args);
    }

}
