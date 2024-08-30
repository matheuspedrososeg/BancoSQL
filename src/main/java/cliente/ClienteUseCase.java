package cliente;

import infra.IniciarConexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;

public class ClienteUseCase {
    Connection connection = IniciarConexao.startConnection();

    public boolean criarUmCliente(Cliente cliente) {
        String query = "insert into cliente(nome, cpf) values('" + cliente.getNomeCliente() + "'," + cliente.getCpf() + ")";
        Statement stm;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(query);
            return true;
        } catch (SQLException | InputMismatchException e) {
            if (e instanceof SQLException) {
                throw new RuntimeException(e);
            }
            if (e instanceof InputMismatchException) {
                System.out.println("Nome ou CPF inserido incorretamente.");
            }
        }
        return false;
    }

    public void consultarCliente() {
        String query = "select * from cliente";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " +
                        rs.getInt("idcliente") +
                        " Nome: " +
                        rs.getString("nome") +
                        " CPF: " +
                        rs.getString("cpf"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void consultarClientePorNome(String nome) {
        String query = "select * from cliente c where nome like '" + nome + "%';";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                System.out.println(
                        "Nome: " +
                                rs.getString("nome") +
                                " CPF: " +
                                rs.getString("cpf"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente consultarIDclientePorNome(String nome) {
        Cliente cliente = new Cliente();
        String query = "select c.idcliente from cliente c where c.nome like '" + nome + "%';";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                 int i = rs.getInt("idcliente");
                 cliente.setIdCliente(i);
                 return cliente;
            }
            else {
                System.out.println("Cliente não encontrado.");
                System.exit(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean formatarBanco() {
        String query = "delete from item_pedido;\n" +
                "delete from pedido;\n" +
                "delete from produto;\n" +
                "delete from cliente;\n" +
                "alter sequence item_pedido_iditempedido_seq restart with 1;\n" +
                "alter sequence pedido_idpedido_seq restart with 1;\n" +
                "alter sequence produto_idproduto_seq restart with 1;\n" +
                "alter sequence cliente_idcliente_seq restart with 1;";
        Statement stm;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
