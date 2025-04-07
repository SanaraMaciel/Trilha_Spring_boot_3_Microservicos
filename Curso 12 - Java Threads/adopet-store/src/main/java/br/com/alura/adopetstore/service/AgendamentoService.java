package br.com.alura.adopetstore.service;

import br.com.alura.adopetstore.email.EmailDeRelatorioGerado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AgendamentoService {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private EmailDeRelatorioGerado enviador;

    //anotacao para configurar o horario que ira executar a tarefa
    @Scheduled(cron = "0 07 10 * * *") //* * * não tem dia, mes ou ano então vai todos dias
    public void envioEmailsAgendado() {
        var estoqueZerado = relatorioService.infoEstoque();
        var faturamentoObtido = relatorioService.faturamentoObtido();

        //o join(). Esse metodo permite que a thread principal (main) aguarde a conclusão de outras threads
        // secundárias antes de prosseguir. deixando as threads sincronizadas
        CompletableFuture.allOf(estoqueZerado, faturamentoObtido).join();

        try {
            enviador.enviar(estoqueZerado.get(), faturamentoObtido.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("thread do agendamento: " + Thread.currentThread().getName());

        //System.out.println(estoqueZerado);
        //System.out.println(faturamentoObtido);

    }
}
