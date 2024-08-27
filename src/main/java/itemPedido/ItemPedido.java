package itemPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pedido.Pedido;
import produto.Produto;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemPedido {
    protected int iditempedido;
    protected int idpedido;
    protected int quantidade;
    protected int idproduto;

    @Override
    public String toString() {
        return "Produto: " + idproduto + " Quantidade: " + quantidade;
    }
}
