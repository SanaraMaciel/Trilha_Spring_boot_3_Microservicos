package com.sanarafelicio.cursomc.domain.enums;

public enum Perfil {

	//prefixo Role antes da palavra é exigência do sprig security 
	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
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
	public static Perfil ToEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for(Perfil x: Perfil.values()){
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		//exceção lançada caso saia do for e ñ ache nenhum valor
		throw new IllegalArgumentException("Id inválido" + cod);
	}
	
}
