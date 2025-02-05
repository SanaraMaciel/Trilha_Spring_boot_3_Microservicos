package com.sanarafelicio.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sanarafelicio.cursomc.domain.Categoria;
import com.sanarafelicio.cursomc.dto.CategoriaDTO;
import com.sanarafelicio.cursomc.repositories.CategoriaRepository;
import com.sanarafelicio.cursomc.security.UserSS;
import com.sanarafelicio.cursomc.services.exceptions.AuthorizationException;
import com.sanarafelicio.cursomc.services.exceptions.DataIntegrityException;
import com.sanarafelicio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	// declarando uma dependência do objeto categoria repository para poder fazer os
	// métodos
	// O @Autowired é uma depoendência q faz com q seja carregado automaticamente
	// pelo spring
	@Autowired
	private CategoriaRepository repo;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imgService;

	@Value("${img.prefix.categoria}")
	private String prefix;

	@Value("${img.categoria.size}")
	private Integer size;

	// pesquisar por id
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj;

	}

	// insert
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	// update
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		// método auxiliar Update Data p/ atualizar os dados desse nojo obj com base no
		// obj q veio como argumento
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	// delete
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	// Listar todas as categorias
	public List<Categoria> findAll() {
		return repo.findAll();
	}

	// Adicionando Paginação
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// método auxiliar para converter de CategoriaDTO para Categoria instancia uma
	// categoria a partir de um DTO
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

	// método auxiliar Update data para salvar apenas os campos desejados em uma
	// tabela relacionamento
	// atualizando os dados do newObj com os dados q vieram do obj
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

	// método para receber a foto de categoria
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		// pegar o usuário logado
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		// instanciando um buffered img
		BufferedImage jpgImage = imgService.getJpgImageFromFile(multipartFile);
		// recortando nossa imagem
		jpgImage = imgService.cropSquare(jpgImage);
		// redimensionando nossa imagem
		jpgImage = imgService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imgService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}

}
