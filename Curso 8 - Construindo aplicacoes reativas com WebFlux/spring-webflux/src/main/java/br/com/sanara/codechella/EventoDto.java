package br.com.sanara.codechella;

import java.time.LocalDate;

public record EventoDto(Long id,
                        TipoEvento tipo,
                        String nome,
                        LocalDate data,
                        String descricao) {

    public static EventoDto toDto(Evento evento) {
        return new EventoDto(evento.getId(), evento.getTipo(), evento.getNome(),
                evento.getData(), evento.getDescricao());
    }

    public Evento toEntity(EventoDto eventoDto) {
        Evento evento = new Evento();
        evento.setId(eventoDto.id);
        evento.setNome(eventoDto.nome);
        evento.setTipo(eventoDto.tipo);
        evento.setData(eventoDto.data);
        evento.setDescricao(eventoDto.descricao);
        return evento;
    }


}
