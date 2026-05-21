package br.com.gabrielclavila.pedidos.processador.service;

import br.com.gabrielclavila.pedidos.processador.entity.ItemPedido;
import br.com.gabrielclavila.pedidos.processador.entity.Pedido;
import br.com.gabrielclavila.pedidos.processador.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public List<ItemPedido> save(List<ItemPedido> itens) {
        return itemPedidoRepository.saveAll(itens);
    }

    // metodo necessário para ser utilizado no metodo de update abaixo
    public void save(ItemPedido item){
        itemPedidoRepository.save(item);
    }

    // metodo necessário devido as chaves serem geradas por meio do UUID
    public void updateItemPedido(List<ItemPedido> itemPedidos, Pedido pedido) {

        itemPedidos.forEach(item -> {
                item.setPedido(pedido); //informando ao item o seu pedido
                this.save(item);
        });
    }
}
