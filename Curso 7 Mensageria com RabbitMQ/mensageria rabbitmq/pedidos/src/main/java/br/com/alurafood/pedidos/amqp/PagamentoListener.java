package br.com.alurafood.pedidos.amqp;

import br.com.alurafood.pedidos.dto.PagamentoDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    //@RabbitListener(queues = "pagamento.concluido") //anotacao de listener que escutara a fila pagamento concluido
    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void recebeMensagem(PagamentoDTO pagamento) {

        //escrevendo a msg com text blocks do java 17
        String mensagem = """
                Dados do pagamento: %s
                Número do pedido: %s
                Valor R$: %
                Status: %s
                """.formatted(pagamento.getId(), pagamento.getPedidoId(), pagamento.getValor(), pagamento.getStatus());

        System.out.println("Recebi a mensagem " + mensagem);
    }

}
