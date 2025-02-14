package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
       // @JsonAlias("data_consulta") ou @JsonAlias({"data_consulta", "data_consulta_medica"}) usado como propertie para dar um alias diferente no cliente front
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") //formata o campo de data para o padrao desejado
        LocalDateTime data,

        Especialidade especialidade) {
}