package br.com.sanara.codechella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {


    private final EventoService servico;

    //é do projeto reactor, que fará um Sink de muitos, no nosso caso, um Flux de EventoDTO
    private final Sinks.Many<EventoDto> eventoSink;

    public EventoController(EventoService servico) {
        this.servico = servico;
        this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
    }


    //flux pode devolver nada ou uma lista de muitos eventos
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterTodos() {
        return servico.obterTodos();
    }


    //O Mono é um stream de eventos que terá zero ou um evento
    @GetMapping("/{id}")
    public Mono<EventoDto> obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }


    @PostMapping
    public Mono<EventoDto> cadastrar(@RequestBody EventoDto dto) {
        //se o cadastro for feito com sucesso doOnSucess ele vai enviar esse evento para o eventoSink
        return servico.cadastrar(dto)
                .doOnSuccess(e -> eventoSink.tryEmitNext(e));
    }


    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id);

    }

    @PutMapping("/{id}")
    public Mono<EventoDto> alterar(@PathVariable Long id, @RequestBody EventoDto dto) {
        return servico.alterar(id, dto);
    }

    //SSE (Server-Sent Events) permite que nosso servidor envie atualizações automáticas através de uma conexão HTTP,
    // de forma que aconteçam em tempo real
    @GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterPorTipo(@PathVariable String tipo) {
        //return servico.obterPorTipo(tipo);
        //return Flux.from(servico.obterPorTipo(tipo))
        return Flux.merge(servico.obterPorTipo(tipo), eventoSink.asFlux()) //para obter o que estiver também no fluxo do merge
                .delayElements(Duration.ofSeconds(4));
    }

}
