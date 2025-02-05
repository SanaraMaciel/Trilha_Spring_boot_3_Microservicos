package com.sanarafelicio.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanarafelicio.cursomc.domain.enums.Perfil;
import com.sanarafelicio.cursomc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//notação @Column(unique = true) faz com que o banco de dados garanta que não haverá repetição nesse campo
	@Column(unique = true)
	private String email;
	
	private String cpfOuCnpj;
	private Integer tipo;
	
	//json ignore para não ficar aparecendo o bcript da senha
	@JsonIgnore
	private String senha;
	
	//private String imageUrl;
	
	//Associação de Cliente com endereço // referência a associação feita na classe endereço no atributo cliente
	//fazer a proteção Json Ciclica da forma que de cliente vc "pega" os endereços mas de endereços não se pega o cliente
	//usando a notação @JsonManagedReference para fazer a proteção cíclica	
	//comportamento em cascata para deleção de endereço ao deletar um cliente
	@OneToMany(mappedBy="cliente",cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	//Associação de clientes com os pedidos 
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>(); 
	
	//constructors
	
	//adicionar para qq usuário que for criado o perfil cliente por default 
	public Cliente() {
		addPerfil(Perfil.CLIENTE);
		
	}

	//este construtor não é necessário ele é criado para facilitar a criação d eum obj através de uma única linha facilitando a
	//instanciação(criação) desse obj. 
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.senha = senha;
		this.tipo = (tipo == null )? null : tipo.getCod();
		addPerfil(Perfil.CLIENTE);
	}

	// getters and setters
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	//método para retornar o perfil dos clientes 
	public Set<Perfil> getPerfis(){
		//expressão lambda
		return perfis.stream().map(x-> Perfil.ToEnum(x)).collect(Collectors.toSet());
	}
	
	//método para adicionar um perfil ao cliente
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	
	public TipoCliente getTipo() {
		//para transformar o cód. em enum chamando o método da enum tipoCliente
		return TipoCliente.ToEnum(tipo) ;
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
