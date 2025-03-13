package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PedidoAMQPConfiguration {

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    //converter o objeto passado pra fila de mensagem para io tipo pagamento
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return  rabbitTemplate;
    }

    //cria a fila - estamos tirando essa responsabilidade dos pagamentos e trazendo para a aplicação de pedidos.
    @Bean
    public Queue filaDetalhesPedido() {
        return QueueBuilder
                .nonDurable("pagamentos.detalhes-pedido")
                .build();
    }

    //cria uma exchange para referenciar o nome dela e aplicar o vínculo (bind).
    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("pagamentos.ex")
                .build();
    }

    // cria o bind entre a fila e a exchange. Assim, ao enviarmos uma mensagem para a exchange,
    // ela será distribuída para as filas vinculadas à ela.
    @Bean
    public Binding bindPagamentoPedido(FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(filaDetalhesPedido())
                .to(fanoutExchange());
    }


    //incluir os beans que usamos no serviço de pagamentos, o do RabbitAdmin e o ApplicationListener.
    //Sem esses beans, não conseguimos criar nada no admin. Por isso replicamos no serviço de pedidos.
    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}