package com.sanarafelicio.cursomc.services;



import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sanarafelicio.cursomc.domain.Categoria;
import com.sanarafelicio.cursomc.domain.Produto;
import com.sanarafelicio.cursomc.repositories.CategoriaRepository;
import com.sanarafelicio.cursomc.repositories.ProdutoRepository;
import com.sanarafelicio.cursomc.security.UserSS;
import com.sanarafelicio.cursomc.services.exceptions.AuthorizationException;
import com.sanarafelicio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	// declarando uma dependência do objeto produto repository para poder fazer os
	// métodos
	// O @Autowired é uma depoendência q faz com q seja carregado automaticamente
	// pelo spring
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired 
	private S3Service s3Service;
	
	@Autowired 
	private ImageService imgService;
	
	@Value("${img.prefix.produto")
	private String prefix;
	
	@Value("${img.produto.size}")
	private Integer size;
	

	public Produto find(Integer id) {
		Produto obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName());
		}
		return obj;
	}
	
	//operação de search
	public Page<Produto> search(String nome,  List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
	//método para receber a foto do produto
			public URI uploadProfilePicture(MultipartFile multipartFile) {
				//pegar o usuário logado
				UserSS user = UserService.authenticated();
				if(user == null) {
					throw new AuthorizationException("Acesso Negado");
				}
				//instanciando um buffered img 
				BufferedImage jpgImage = imgService.getJpgImageFromFile(multipartFile);
				//recortando nossa imagem
				jpgImage = imgService.cropSquare(jpgImage);
				//redimensionando nossa imagem
				jpgImage = imgService.resize(jpgImage, size);
				
				String fileName = prefix + user.getId() + ".jpg";
				 
				return s3Service.uploadFile(imgService.getInputStream(jpgImage,"jpg"), fileName,"image");
			}
	
}
