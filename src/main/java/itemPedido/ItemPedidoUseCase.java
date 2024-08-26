package itemPedido;

import infra.IniciarConexao;
import pedido.Pedido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemPedidoUseCase {
    Connection connection = IniciarConexao.startConnection();

    public boolean criarItemPedido(ItemPedido itemPedido, int idpedido) {
        String query = "insert into item_pedido(idpedido, quantidade, idproduto) values (" +
                idpedido + "," +
                itemPedido.getQuantidade() + "," +
                itemPedido.getIdproduto() + ")";
        Statement stm;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public float totalValor(Pedido pedido) {
        String query = "select " +
                "ip.idproduto, p.valor, ip.quantidade from produto p " +
                    "left outer join item_pedido ip on p.idproduto = ip.idproduto where idpedido =" + pedido.getIdpedido() + ";";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            float total = 0;
            while (rs.next()) {
                int qt = (rs.getInt("quantidade"));
                float valor = (rs.getFloat("valor"));
                total = total + (qt * valor);
            }
            pedido.setTotal(total);
            String totalSet = "update pedido set total = "+ pedido.getTotal() +" where idpedido = " + pedido.getIdpedido() + ";";
            stm.executeUpdate(totalSet);
            return total;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
