package com.sanarafelicio.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	//método Encode para descodificar um parâmetro ou seja, remover o espaço em branco (%20)
	public static String decodeParam(String s) {
			try {
				return URLDecoder.decode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "";
				
			}
		}

	public static List<Integer> decodeInList(String s) {

		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		
		//mesmo método de cima só que usando função lambda
		//return Arrays.asList(s.split(",")).stream().map(x->Integer.parseInt(x)).collect(Collectors.toList());
	}

}
