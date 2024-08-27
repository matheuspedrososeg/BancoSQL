package pedido;

import infra.IniciarConexao;
import itemPedido.ItemPedido;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PedidoUseCase {
    Connection connection = IniciarConexao.startConnection();
    public boolean criarPedidoSemTotal(Pedido pedido) {
        String query = "insert into pedido(idcliente) values (" +
                pedido.getIdcliente() + ")";
        String Id = "select max(idpedido) from pedido;";
        Statement stm;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(query);
            ResultSet rs = stm.executeQuery(Id);
            rs.next();
            pedido.setIdpedido(rs.getInt("max"));
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String consultarPedido(String nome) {
        String query = "select c.nome, p.idpedido, p.total from pedido p left outer join cliente c on p.idcliente = c.idcliente where c.nome like '" + nome +"%';";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (!rs.next()) {
                return null;
            }
            while (rs.next()) {
                System.out.println("Nome cliente: " +
                        rs.getString("nome") +
                        " ID do pedido: " +
                        rs.getInt("idpedido") +
                        " Total: " +
                        rs.getFloat("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "1";
    }
    public void mostrarCarrinho(Pedido pedido) {
        for (int i = 0; i < pedido.getItensPedido().size(); i++) {
            String query = "select p.nome as nome from produto p where p.idproduto = " + pedido.getItensPedido().get(i).getIdproduto() + ";";
            Statement stm;
            try {
                stm = connection.createStatement();
                ResultSet rs = stm.executeQuery(query);
                while (rs.next()) {
                    System.out.println("Produto: " + rs.getString("nome") + " Quantidade: " + pedido.getItensPedido().get(i).getQuantidade());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void printarLista(int idpedido) {
        String query = "select p.nome as produto, ip.quantidade from item_pedido ip " +
                "left outer join produto p on ip.idproduto = p.idproduto where idpedido = " + idpedido + ";";
        Statement stm;
        try {
            stm = connection.createStatement();
             ResultSet rs = stm.executeQuery(query);
             while (rs.next()) {
                 System.out.println("Produto: " + rs.getString("produto") + " Quantidade: " + rs.getInt("quantidade"));
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
