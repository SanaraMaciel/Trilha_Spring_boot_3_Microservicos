package com.sanarafelicio.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sanarafelicio.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento{
	
	private static final long serialVersionUID = 1L;
	
	//anotação para formatar a data
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date datavencimento;
	
	//anotação para formatar a data
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataPagamento;
	
	//constructors
	public PagamentoComBoleto() {
		
	}

	//gerar este construtor como : Source- generate constructor from superclass adicionando os atributos locais
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido,Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.setDataPagamento(dataPagamento);
		this.setDatavencimento(dataVencimento);
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDatavencimento() {
		return datavencimento;
	}

	public void setDatavencimento(Date datavencimento) {
		this.datavencimento = datavencimento;
	}
	
	
	
	
	

}
