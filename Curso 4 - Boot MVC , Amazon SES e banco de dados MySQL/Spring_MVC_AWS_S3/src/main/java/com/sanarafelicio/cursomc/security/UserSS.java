package com.sanarafelicio.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sanarafelicio.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserSS() {
		
	}
	
		
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis ) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		//convertendo o conjunto de perfil para a lista de Authority
		this.authorities = perfis.stream().map(x-> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}



	public Integer getId() {
		return id;
	}	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	//conta não expirada
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//conta não bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//as credenciais não estão expiradas
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//usuário está habilitado(ativo)
	@Override
	public boolean isEnabled() {
		return true;
	}

	//método para recuperar o perfil(papel) do usuário logado
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
	
}
