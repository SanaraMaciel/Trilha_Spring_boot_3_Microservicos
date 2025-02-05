package com.sanarafelicio.cursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sanarafelicio.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento{
	//número de versão não é necessário fazer a classe implementar o Serializable pq a super classe já implementa então ela herda 
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;
	
	//constructors
	public PagamentoComCartao() {
		
	}

	//constructor from superclass
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.setNumeroDeParcelas(numeroDeParcelas);
	}

	//getter and setters
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	

	
}
