package br.com.gabrielclavila.pedidos.notificacao.listener;

import br.com.gabrielclavila.pedidos.notificacao.entity.Pedido;
import br.com.gabrielclavila.pedidos.notificacao.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {

    public final Logger logger = LoggerFactory.getLogger(PedidoListener.class);

    private final EmailService emailService;

    public PedidoListener(EmailService emailService){
        this.emailService = emailService;
    }

    @RabbitListener(queues = "pedidos.v1.pedido-criado.gerar-notificacao")
    public void enviarNotificacao(Pedido pedido){

        // Simulando uma Exception que causará um loop infinito no recebimento das mensagens, pois a mensagem e enviada para o RabbitMQ
        // Onde ele tenta processar a mensagem e como está com um problema por ser uma Exception,
        // ele reenvia a mensagem para fila e tenta processar novamente e fica nesse ciclo causando o loop infinito
        if(pedido.getValorTotal() > 2000) {
            throw new RuntimeException("Valor muito alto");
        }

        emailService.enviarEmail(pedido);
        logger.info("Notificação gerada: {}", pedido.toString());
    }
}
