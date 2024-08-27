import infra.IniciarConexao;
import usuarioMethods.ComandosUsuario;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        ComandosUsuario cmu = new ComandosUsuario();
        cmu.start();
    }

    // pegar cliente pelo seu objeto, e nao por ID.
    // corrigir consulta pedido, por causa de !rs.next() ele nao pega o primeiro pedido feito.

}
