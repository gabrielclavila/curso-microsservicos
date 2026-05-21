package br.com.gabrielclavila.pedidos.processador.listener;

import br.com.gabrielclavila.pedidos.processador.entity.Pedido;
import br.com.gabrielclavila.pedidos.processador.entity.enums.Status;
import br.com.gabrielclavila.pedidos.processador.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    private final Logger logger = LoggerFactory.getLogger(PedidoListener.class);

    private final PedidoService pedidoService;

    //Injeção de Dependência por meio do construtor da classe
    public PedidoListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = "pedidos.v1.pedido-criado.gerar-processamento")
    private void salvarPedido(Pedido pedido){
        pedido.setStatus(Status.PROCESSADO);
        pedidoService.save(pedido);
    }
}
