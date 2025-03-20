package br.com.sanara.codechella;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {

    Flux<Evento> findByTipo(TipoEvento tipoEvento);
}
