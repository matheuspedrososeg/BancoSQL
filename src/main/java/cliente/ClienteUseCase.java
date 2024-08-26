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

    public int consultarIDclientePorNome(String nome) {
        String query = "select c.idcliente from cliente c where c.nome like '" + nome + "%';";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("idcliente");
            }
            else {
                System.out.println("Cliente nao encontrado.");
                System.exit(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

}
