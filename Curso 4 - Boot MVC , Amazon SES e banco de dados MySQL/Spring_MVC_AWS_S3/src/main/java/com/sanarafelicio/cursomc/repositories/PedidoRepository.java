package com.sanarafelicio.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sanarafelicio.cursomc.domain.Cliente;
import com.sanarafelicio.cursomc.domain.Pedido;

@Repository													//objeto pedido e tipo do idntificador id que é Integer				
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	//método para cliente somete ver os seus pedidos
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
