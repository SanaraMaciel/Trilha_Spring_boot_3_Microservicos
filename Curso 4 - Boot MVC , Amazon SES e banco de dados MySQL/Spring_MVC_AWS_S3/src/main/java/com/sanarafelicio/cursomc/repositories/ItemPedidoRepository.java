package com.sanarafelicio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanarafelicio.cursomc.domain.ItemPedido;

@Repository													//objeto ItemPedido e tipo do idntificador id que é Integer				
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{

}
