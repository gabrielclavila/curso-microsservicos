package br.com.gabrielclavila.pedidos.notificacao.service;

import br.com.gabrielclavila.pedidos.notificacao.entity.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Injeção de Depedência feita por meio do Construtor da Classe, conforme recomenda o SpringBoot como boa prática,
    // mas poderia ser feito também por meio da notação @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void enviarEmail(Pedido pedido){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("gabriel.clavila@gmail.com");
        simpleMailMessage.setTo(pedido.getEmailNotificacao());
        simpleMailMessage.setSubject("Pedido de Compra");
        simpleMailMessage.setText(this.gerarMensagem(pedido));
        mailSender.send(simpleMailMessage);
    }

    private String gerarMensagem(Pedido pedido){
        String pedidoId = pedido.getId().toString();
        String cliente = pedido.getCliente();
        String valor = String.valueOf(pedido.getValorTotal());
        String status = pedido.getStatus().name();
        return String.format("Olá %s seu pedido de n %s no valor de %s, foi realizado com sucesso.\nStatus: %s.", cliente, pedidoId, valor, status);
    }
}
