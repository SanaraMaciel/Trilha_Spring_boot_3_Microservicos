package br.com.sanara.pagamentos.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoAMQPConfiguration {

    @Bean
    public Queue criaFila() {
        //false significa que a fila não é duravel
        return new Queue("pagamento.concluido", false);
    }
}


