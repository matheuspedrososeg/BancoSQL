package usuarioMethods;

import cliente.Cliente;
import cliente.ClienteUseCase;
import itemPedido.ItemPedido;
import itemPedido.ItemPedidoUseCase;
import pedido.Pedido;
import pedido.PedidoUseCase;
import produto.Produto;
import produto.ProdutoUseCase;

import java.util.Scanner;

public class ComandosUsuario {
    Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Qual tabela deseja manipular?");
        System.out.println("1 = Produto\n2 = Pedido\n3 = Cliente\n0 = Finalizar programa.");
        int choice = scanner.nextInt();
        while (choice != 0) {

            if (choice == 1) {
                System.out.println("O que deseja fazer?");
                System.out.println("1 = Adicionar produto.\n2 = Remover produto\n3 = Consultar produto.");
                int choice1 = scanner.nextInt();
                if (choice1 == 1) {
                    addProduto();
                }
                if (choice1 == 2) {
                    removeProduto();
                }
                if (choice1 == 3) {
                    consultarProduto();
                }

            }
            // pedido
            else if (choice == 2) {
                System.out.println("O que deseja fazer?");
                System.out.println("1 = Criar um pedido.\n2 = Consultar um pedido ");
                int choice2 = scanner.nextInt();
                if (choice2 == 1) {
                    Pedido pedido = new Pedido();
                    ItemPedidoUseCase it = new ItemPedidoUseCase();
                    iniciarPedido(pedido);
                    System.out.println("Deseja comecar a inserir produtos no seu pedido? (y/n)");
                    char cont = scanner.next().charAt(0);
                    while (cont != 'y' && cont != 'n') {
                        System.out.println("Escolha invalida seu safado. Tente novamente");
                        cont = scanner.next().charAt(0);
                    }
                    while (cont != 'n') {
                        criarItemPedido(pedido.getIdpedido());
                        System.out.println("\nValor atual do seu pedido:" + it.totalValor(pedido) + "\n");
                        System.out.println("Deseja continuar a inserir produtos no seu pedido? (y/n)");
                        cont = scanner.next().charAt(0);
                        if (cont == 'n') {
                            System.exit(0);
                        }
                    }

                } else if (choice2 == 2) {
                    consultarPedido();
                }
            }
            // cliente
            else if (choice == 3) {
                ClienteUseCase cliente = new ClienteUseCase();
                System.out.println("Deseja criar, ou consultar um cliente por nome, ou consultar os clientes? (1, 2, 3)");
                int escolhaCliente = scanner.nextInt();
                if (escolhaCliente == 1) {
                    addCliente();
                } else if (escolhaCliente == 2) {
                    System.out.println("Insira o nome do cliente a ser consultado.");
                    scanner.nextLine();
                    String nome = scanner.nextLine();
                    cliente.consultarClientePorNome(nome);
                }
                else if (escolhaCliente == 3) {
                    cliente.consultarCliente();
                }
            }
            else if (choice > 3 || choice < 1) {
                System.out.println("Escolha nao disponivel.");
                System.exit(0);
            }

            System.out.println("Deseja continuar? (y/n)");
            char continuar = scanner.next().charAt(0);
            if (continuar == 'n') {
                choice = 0;
            } else {
                System.out.println("Qual tabela deseja manipular?");
                System.out.println("1 = Produto\n2 = Pedido\n3 = Cliente\n0 = Finalizar programa.");
                choice = scanner.nextInt();
            }
        }

    }

    // Produto metodos:

    public void addProduto() {
        ProdutoUseCase produtoUseCase = new ProdutoUseCase();
        scanner.nextLine();
        System.out.println("Insira o nome de seu produto.");
        String nome = scanner.nextLine();
        System.out.println("Insira o valor de seu produto.");
        float valor = scanner.nextFloat();
        Produto produto = Produto.builder().nome(nome).valor(valor).build();
        if (produtoUseCase.criarProduto(produto)) System.out.println("Produto adicionado com sucesso.");
    }

    public void removeProduto() {
        ProdutoUseCase produtoUseCase = new ProdutoUseCase();
        System.out.println("Insira o ID do produto a ser deletado.");
        int id = scanner.nextInt();
        if (produtoUseCase.deletarProdutoPorID(id)) System.out.println("Produto deletado com sucesso.");
    }

    public void consultarProduto() {
        ProdutoUseCase produtoUseCase = new ProdutoUseCase();
        produtoUseCase.consultarProdutos();
    }
    // Cliente

    public void addCliente() {
        ClienteUseCase cl = new ClienteUseCase();
        scanner.nextLine();
        System.out.println("Insira o nome do Cliente");
        String nome = scanner.nextLine();
        System.out.println("Insira o CPF (Sem pontos ou tracos.");
        long cpf = scanner.nextLong();
        while (cpf < 11111111111L || cpf > 99999999999L) {
            System.out.println("CPF invalido, tente novamente.");
            cpf = scanner.nextLong();
        }
        Cliente cliente = Cliente.builder().nomeCliente(nome).cpf(String.valueOf(cpf)).build();
        if (cl.criarUmCliente(cliente)) System.out.println("Cliente adicionado com sucesso!");

    }

    // ItemPedido e Pedido metodos:


    public void iniciarPedido(Pedido ped) {
        PedidoUseCase pd = new PedidoUseCase();
        ClienteUseCase cliente = new ClienteUseCase();
        System.out.println("Insira o nome do cliente. (Caso nao tenha o nome de um cliente, abra o programa novamente e crie um.)");
        scanner.nextLine();
        String nome = scanner.nextLine();
        ped.setIdcliente(cliente.consultarIDclientePorNome(nome));
        pd.criarPedidoSemTotal(ped);
    }


    public void criarItemPedido(int idpedido) {
        ItemPedidoUseCase it = new ItemPedidoUseCase();
        ProdutoUseCase pr = new ProdutoUseCase();
        System.out.println("Tabela de Produtos disponiveis:");
        pr.consultarProdutos();
        scanner.nextLine();
        System.out.println("Agora insira o nome do produto ao seu pedido.");
        String nome = scanner.nextLine();
        int i = pr.consultarIDItemPorNome(nome);
        while (i == 0) {
            System.out.println("Insira o produto novamente.");
            nome = scanner.nextLine();
            i = pr.consultarIDItemPorNome(nome);
        }
        System.out.println("Quantos deste produto deseja comprar?");
        int qt = scanner.nextInt();

        ItemPedido itpedido = ItemPedido.builder().idpedido(idpedido).quantidade(qt).idproduto(i).build();
        if (it.criarItemPedido(itpedido, idpedido)) {
            System.out.println("Item adicionado ao pedido com sucesso.");
        } else {
            System.out.println("Falha ao inserir item.");
        }
    }

    public void consultarPedido() {
        PedidoUseCase pd = new PedidoUseCase();
        System.out.println("Insira o nome do cliente que fez o pedido.");
        scanner.nextLine();
        String nome = scanner.nextLine();
        pd.consultarPedido(nome);
    }

}
