package produto;

import infra.IniciarConexao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoUseCase {
    Connection connection = IniciarConexao.startConnection();
    public void consultarProdutoPorID( int id) {
        String query = "select nome, valor from produto where idproduto = " + id;
        Statement stm;

        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                System.out.println("Produto: " + rs.getString("nome") + " \nValor: " + rs.getInt("valor"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void consultarProdutos() {
        Statement stm;
        String query = "select * from produto;";
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("idproduto")
                            + " Nome: " + rs.getString("nome")
                                + " Valor: " + rs.getFloat("valor"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean criarProduto(Produto produto) {
        String query = "insert into produto (nome, valor) values ('" + produto.getNome() + "'," + produto.getValor() + ")";
        Statement stm;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletarProdutoPorID(int id) {
        String query = "delete from produto where idproduto =" + id;
        Statement stm;
        try {
            stm = connection.createStatement();
            stm.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int consultarIDItemPorNome(String nome) {
        String query = "select p.idproduto from produto p where p.nome like '" + nome + "%';";
        Statement stm;
        try {
            stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("idproduto");
            }
            else {
                System.out.println("Produto nao encontrado.");
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
