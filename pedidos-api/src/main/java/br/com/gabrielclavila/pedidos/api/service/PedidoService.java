package br.com.gabrielclavila.pedidos.api.service;

import br.com.gabrielclavila.pedidos.api.entity.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    public PedidoService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    // Outra forma de implementar os código acima relacionados ao RabbitTemplate seria usando a Injeção de Dependência
    // Porém é uma boa prática fazer como está acima utilizando a Injeção via construtor da classe
    //@Autowired
    //private RabbitTemplate rabbitTemplate;

    public Pedido enfileirarPedido(Pedido pedido){
        rabbitTemplate.convertAndSend(exchangeName, "", pedido);
        logger.info("Pedido enfileirado: {}", pedido.toString());
        return pedido;
    }
}
