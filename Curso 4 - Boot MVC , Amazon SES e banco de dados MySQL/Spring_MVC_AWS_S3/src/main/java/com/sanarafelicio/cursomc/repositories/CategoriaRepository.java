package com.sanarafelicio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanarafelicio.cursomc.domain.Categoria;

@Repository													//objeto categoria e tipo do idntificador id que é Integer				
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
