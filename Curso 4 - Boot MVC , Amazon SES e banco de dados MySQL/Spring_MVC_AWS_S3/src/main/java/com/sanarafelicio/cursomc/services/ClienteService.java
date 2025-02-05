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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sanarafelicio.cursomc.domain.Cidade;
import com.sanarafelicio.cursomc.domain.Cliente;
import com.sanarafelicio.cursomc.domain.Endereco;
import com.sanarafelicio.cursomc.domain.enums.Perfil;
import com.sanarafelicio.cursomc.domain.enums.TipoCliente;
import com.sanarafelicio.cursomc.dto.ClienteDTO;
import com.sanarafelicio.cursomc.dto.ClienteNewDTO;
import com.sanarafelicio.cursomc.repositories.ClienteRepository;
import com.sanarafelicio.cursomc.repositories.EnderecoRepository;
import com.sanarafelicio.cursomc.security.UserSS;
import com.sanarafelicio.cursomc.services.exceptions.AuthorizationException;
import com.sanarafelicio.cursomc.services.exceptions.DataIntegrityException;
import com.sanarafelicio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService { 
	
	//dependência do bean para criptografar a senha
	@Autowired
	private BCryptPasswordEncoder pe; 

	//declarando uma dependência do objeto cliente repository para poder fazer os métodos
	//O @Autowired é uma dependência q faz com q seja carregado automaticamente pelo spring 
	@Autowired
	private ClienteRepository repo;
	
	@Autowired  
	private EnderecoRepository enderecoRepository;
	
	@Autowired 
	private S3Service s3Service;
	
	@Autowired 
	private ImageService imgService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
		
	public Cliente find(Integer id) {
		
		//Adicionando restrição para o cliente só poder recuperar o perfil dele mesmo
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		
		Cliente obj = repo.findOne(id);
		if (obj == null) {
		throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
		+ ", Tipo: " + Cliente.class.getName());
		}
		return obj;
		
	}
	
	//insert
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}
	
	//update
		public Cliente update(Cliente obj) {
			Cliente newObj = find(obj.getId());
			//método auxiliar Update Data p/ atualizar os dadso desse nojo obj com base no obj q veio como argumento
			updateData(newObj, obj);			
			return repo.save(newObj);			
		}
		
		//delete
		public void delete(Integer id) {
			find(id);
			try {
				repo.delete(id);
			}catch(DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos"); 
			}
		}
		
		//Listar todas os clientes
		public List<Cliente> findAll(){
			return repo.findAll();
		}
		
		//Buscar Cliente por email
		public Cliente findByEmail(String email) {
			UserSS user = UserService.authenticated();
			if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
				throw new AuthorizationException("Acesso Negado");
			}
			
			Cliente obj = repo.findByEmail(email);
			if(obj == null) {
				throw new ObjectNotFoundException("Objeto não encontrado: " + user.getId()
				+ "Tipo: " + Cliente.class.getName());
			}
			return obj;			
		}
		
		
		//Adicionando Paginação 
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
			return repo.findAll(pageRequest);		
		}
		
		//método auxiliar para converter de ClienteDTO para Cliente instancia uma categoria a partir de um DTO
		public Cliente fromDTO(ClienteNewDTO objDto) {
			Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.ToEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));
			Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
			Endereco end = new Endereco(null, objDto.getLongradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());
			if (objDto.getTelefone2()!=null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			if (objDto.getTelefone3()!=null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			return cli;
		}
		
		public Cliente fromDTO(ClienteDTO objDto) {
			return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null, null);	
		}
		
		
		//método auxiliar Update data para salvar apenas os campos desejados em uma tabela relacionamento
		//atualizando os dados do newObj com os dados q vieram do obj
		private void updateData(Cliente newObj, Cliente obj ) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());			
		}
		
		//método para receber a foto de perfil do cliente
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
			
			//usado para salvar a url da imagem no banco
			//URI uri =  s3Service.uploadFile(multipartFile);
			//pega o id do cliente logado e cria um cliente com ele
			//Cliente cli = repo.findOne(user.getId());
			//cli.setImageUrl(uri.toString());
			//repo.save(cli);
			//return uri;
		}
}
