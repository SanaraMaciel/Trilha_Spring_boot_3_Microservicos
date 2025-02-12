package med.voll.api.domain.paciente;

public record DadosExibicaoPaciente(Long id, String nome, String email, String cpf, String telefone) {


    public DadosExibicaoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone());
    }
}


