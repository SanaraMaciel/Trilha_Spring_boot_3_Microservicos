package br.com.alurafood.pedidos.amqp;

import br.com.alurafood.pedidos.dto.PagamentoDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    //@RabbitListener(queues = "pagamento.concluido") //anotacao de listener que escutara a fila pagamento concluido
    @RabbitListener(queues = "pagamentos.detalhes-pedido")//anotacao q mostra q vai ser usada na exchange pagamentos.detalhes-pedido
    public void recebeMensagem(@Payload PagamentoDTO pagamento) {

        System.out.println(pagamento.getId());
        System.out.println(pagamento.getNumero());

        if (pagamento.getNumero().equals("0000")) {
            throw new RuntimeException("não consegui processar");
        }

        //escrevendo a msg com text blocks do java 17
        String mensagem = """
                Dados do pagamento: %s
                Número do pedido: %s
                Valor R$: %s
                Status: %s
                """.formatted(pagamento.getId(), pagamento.getPedidoId(), pagamento.getValor(), pagamento.getStatus());

        System.out.println("Recebi a mensagem " + mensagem);
    }

}
