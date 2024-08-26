package pedido;

import infra.IniciarConexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void consultarPedido(String nome) {
        String query = "select c.nome, p.idpedido, p.total from pedido p left outer join cliente c on p.idcliente = c.idcliente where c.nome like '" + nome +"%';";
        Pedido pedido = new Pedido();
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
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
    }

}
