package com.sanarafelicio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanarafelicio.cursomc.domain.Pagamento;

@Repository													//objeto pagamento e tipo do idntificador id que é Integer				
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

	
	//basta criar o repositorie da superclasse se vc criar o da super classe não é necessário 
	//criar das classes filhas pois elas herdam
	
}
