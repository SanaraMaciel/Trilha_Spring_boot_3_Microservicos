package com.sanarafelicio.cursomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sanarafelicio.cursomc.services.S3Service;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	//@Autowired
	//private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//código de teste do S3
		//s3Service.uploadFile("C:\\Users\\Sanara\\eclipse-workspace\\cursomc\\Imagens\\img.jpg");
	
		
	}
	
	
}
