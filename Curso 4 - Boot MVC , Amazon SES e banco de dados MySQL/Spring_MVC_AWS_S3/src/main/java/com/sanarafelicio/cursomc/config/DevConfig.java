package com.sanarafelicio.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.sanarafelicio.cursomc.services.DBService;
import com.sanarafelicio.cursomc.services.EmailService;
import com.sanarafelicio.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	//configuração para detectar se é create : spring.jpa.hibernate.ddl-auto=create 
	// armazena o valor na var strategy e se for create faz a lógica if para instanciar o banco
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;
		}
		
		dbService.instantiateTestDatabase();
		return true;		
	} 
	
	//bean para envio de email google 	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
	
	
}
