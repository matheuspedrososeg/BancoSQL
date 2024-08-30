package pedido;

import cliente.Cliente;
import itemPedido.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    protected int idpedido;
    protected int idcliente;
    protected List<ItemPedido> itensPedido;
    protected float total;
}
