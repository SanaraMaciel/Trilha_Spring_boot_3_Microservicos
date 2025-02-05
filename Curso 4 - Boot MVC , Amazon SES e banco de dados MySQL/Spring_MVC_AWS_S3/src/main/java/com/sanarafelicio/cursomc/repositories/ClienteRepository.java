package com.sanarafelicio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanarafelicio.cursomc.domain.Cliente;

@Repository													//objeto cliente e tipo do idntificador id que é Integer				
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	//pesquisar por email
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
