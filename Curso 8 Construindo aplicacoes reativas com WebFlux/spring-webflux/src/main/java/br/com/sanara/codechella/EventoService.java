package br.com.sanara.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repositorio;


    public Flux<EventoDto> obterTodos() {
        return repositorio.findAll()
                .map(EventoDto::toDto);
    }

    //O switchIfEmpty é porque o Mono como ele sempre retorna 0 ou 1 ele assume que o 0 eh ok e retorna 200
    //nesse caso precisamos forcar o metodo a retornar uma excecao caso nao encontre o valor
    public Mono<EventoDto> obterPorId(Long id) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDto::toDto);
    }


    public Mono<EventoDto> cadastrar(EventoDto dto) {
        return repositorio.save(dto.toEntity(dto))
                .map(EventoDto::toDto);
    }


    public Mono<Void> excluir(Long id) {
        return repositorio.findById(id)
                .flatMap(repositorio::delete);
    }


    public Mono<EventoDto> alterar(Long id, EventoDto dto) {
        return repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do evento não encontrado.")))
                .flatMap(eventoExistente -> {
                    eventoExistente.setTipo(dto.tipo());
                    eventoExistente.setNome(dto.nome());
                    eventoExistente.setData(dto.data());
                    eventoExistente.setDescricao(dto.descricao());
                    return repositorio.save(eventoExistente);
                })
                .map(EventoDto::toDto);
    }


    public Flux<EventoDto> obterPorTipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return repositorio.findByTipo(tipoEvento)
                .map(EventoDto::toDto);
    }

    public Mono<String> obterTraducao(Long id, String idioma) {
        return repositorio.findById(id)
                .flatMap(e -> TraducaoDeTextos.obterTraducao(e.getDescricao(), idioma));
    }

}

