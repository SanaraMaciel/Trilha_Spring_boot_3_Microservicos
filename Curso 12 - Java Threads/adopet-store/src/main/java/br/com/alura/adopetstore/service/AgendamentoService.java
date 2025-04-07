package br.com.alura.adopetstore.service;

import br.com.alura.adopetstore.email.EmailDeRelatorioGerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private EmailDeRelatorioGerado enviador;

    //anotacao para configurar o horario que ira executar a tarefa
    @Scheduled(cron = "0 40 9 * * *") //* * * não tem dia, mes ou ano então vai todos dias
    public void envioEmailsAgendado() {
        var estoqueZerado = relatorioService.infoEstoque();
        var faturamentoObtido = relatorioService.faturamentoObtido();

        enviador.enviar(estoqueZerado, faturamentoObtido);

        System.out.println("thread do agendamento: " + Thread.currentThread().getName());

        //System.out.println(estoqueZerado);
        //System.out.println(faturamentoObtido);
    }
}
