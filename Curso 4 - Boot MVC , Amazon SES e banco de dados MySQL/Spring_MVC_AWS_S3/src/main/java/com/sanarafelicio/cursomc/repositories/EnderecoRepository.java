package com.sanarafelicio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanarafelicio.cursomc.domain.Endereco;

@Repository													//objeto endereco e tipo do idntificador id que é Integer				
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

}
