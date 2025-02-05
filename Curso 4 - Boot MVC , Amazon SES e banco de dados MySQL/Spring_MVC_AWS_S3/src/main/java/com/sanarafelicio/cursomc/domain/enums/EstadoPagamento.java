package com.sanarafelicio.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"), 
	CANCELADO(3,"Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	//gerar apenas o métodos getters pois uma vez que se atribui valor a uma enumeração não é mais necessário alterar esse valor!
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}

	//método para retornar o tipo enum static pq ele deve ser capaz de executar msm se não instanciar um obj
	public static EstadoPagamento ToEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for(EstadoPagamento x: EstadoPagamento.values()){
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		//exceção lançada caso saia do for e ñ ache nenhum valor
		throw new IllegalArgumentException("Id inválido" + cod);
	}
	
}
