package br.com.alurafood.pagamentos.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoAMQPConfiguration {

    @Bean
    public Queue criaFila() {

        //false significa que a fila não é duravel
        //return new Queue("pagamento.concluido", false);

        //outra forma de retornar a fila : com o builder do queue nonDurable
        return QueueBuilder.nonDurable("pagamento.concluido").build();

    }

    //Para fazer operações administrativas no Rabbit eh necessario um metodo que retorne um RabbitADmin
    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    //nicializar o RabbitMQ ao subirmos a aplicação, isto é, sincronizar para conseguir realizar as operações.
    // Para isso, vamos usar uma interface chamada ApplicationListener.
    //Para analisarmos uma característica dessa interface, vamos acessá-la clicando em "ApplicationListener". Note que ela é uma Functional Interface, que dispara um evento.
    //Usaremos esse padrão return event -> para mandar inicializar o RabbitMQ.
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

}


