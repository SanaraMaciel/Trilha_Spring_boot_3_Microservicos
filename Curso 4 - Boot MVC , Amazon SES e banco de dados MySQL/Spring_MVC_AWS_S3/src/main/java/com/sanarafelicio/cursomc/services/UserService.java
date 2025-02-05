package com.sanarafelicio.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.sanarafelicio.cursomc.security.UserSS;

public class UserService {
	
	//método para rtornar o usuário logado
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
		
	}

}
