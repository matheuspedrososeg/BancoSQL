package cliente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cliente {
    protected int idCliente;
    protected String nomeCliente;
    protected String cpf;
}
