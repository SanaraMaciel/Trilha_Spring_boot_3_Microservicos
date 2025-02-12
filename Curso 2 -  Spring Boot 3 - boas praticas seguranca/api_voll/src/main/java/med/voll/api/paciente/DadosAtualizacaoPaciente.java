package med.voll.api.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(Long id, String nome, String telefone, @Valid DadosEndereco endereco) {

    public DadosAtualizacaoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getTelefone(), null);
    }
}