package br.com.alura.adopetstore.email;

import br.com.alura.adopetstore.dto.PedidoDTO;
import br.com.alura.adopetstore.dto.RelatorioEstoque;
import br.com.alura.adopetstore.dto.RelatorioFaturamento;
import br.com.alura.adopetstore.model.Usuario;
import br.com.alura.adopetstore.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmailDeRelatorioGerado {
    @Autowired
    private EnviadorEmail enviador;


    public void enviar(RelatorioEstoque estoque, RelatorioFaturamento faturamento) {

        enviador.enviarEmail(
                "Relatorios gerados",
                "admin@email.com",
                """
                            Olá! seus relatorios foram gerados e seguem abaixo:
                            
                            %s
                            
                            %s
                            
                        """.formatted(estoque, faturamento)
        );

    }
}

