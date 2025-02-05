package com.sanarafelicio.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.sanarafelicio.cursomc.domain.Cliente;
import com.sanarafelicio.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")	
	private String nome;
	
	
	@NotEmpty(message="Preenchimeneto Obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	public ClienteDTO() {
		
	}
	
	//construtor que recebe o cliente DTo e o instancia criando um cliente dto
	public ClienteDTO(Cliente obj) {
		id= obj.getId();
		nome = obj.getNome();
		email= obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
