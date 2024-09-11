package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosAtualizacaoMedico;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {

	@Autowired
	private MedicoRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
		repository.save(new Medico(dados));
	}

	// retornando o método com listagem de dto
	@GetMapping("/list")
	public List<DadosListagemMedico> listar() {
		// itera sobre a lista de médicos convertendo em um dto com os dados
		return repository.findAll().stream().map(DadosListagemMedico::new).toList();
	}

	// retornando o método com paginação de dto
	@GetMapping
	public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return repository.findAll(paginacao).map(DadosListagemMedico::new);
	}

	@GetMapping("listaMedicosAtivos")
	public Page<DadosListagemMedico> listarMedicosAtivos(
			@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
	}

	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = repository.getReferenceById(dados.id());// busca a referencia da entidade pelo id
		medico.atualizarInformacoes(dados);
	}

	// deleção total do objeto do banco de dados
	@DeleteMapping("/deleteBanco/{id}")
	@Transactional
	public void excluirBanco(@PathVariable Long id) {
		repository.deleteById(id);
	}

	// deleção lógica do objeto, ou seja, ele não é apagado do banco fica apenas
	// inativo no sistema
	@DeleteMapping("/{id}")
	@Transactional
	public void excluir(@PathVariable Long id) {
		var medico = repository.getReferenceById(id);
		medico.excluir();
	}

}