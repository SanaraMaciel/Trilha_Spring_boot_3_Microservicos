package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(Long id, String nome, String telefone, @Valid DadosEndereco endereco) {

    public DadosAtualizacaoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getTelefone(), null);
    }
}