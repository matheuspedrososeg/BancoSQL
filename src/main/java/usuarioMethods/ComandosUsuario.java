package usuarioMethods;

import cliente.Cliente;
import cliente.ClienteUseCase;
import itemPedido.ItemPedido;
import itemPedido.ItemPedidoUseCase;
import pedido.Pedido;
import pedido.PedidoUseCase;
import produto.Produto;
import produto.ProdutoUseCase;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComandosUsuario {
    Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Qual tabela deseja manipular?");
        System.out.println("1 = Produto\n2 = Pedido\n3 = Cliente\n0 = Finalizar programa.");
        int choice = scanner.nextInt();
        PedidoUseCase pd = new PedidoUseCase();
        while (choice != 0) {

            while (choice > 3 || choice < 0) {
                System.out.println("Escolha nao disponivel. Tente novamente.");
                System.out.println("1 = Produto\n2 = Pedido\n3 = Cliente\n0 = Finalizar programa.");
                choice = scanner.nextInt();
            }

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

                while (choice2 > 2 || choice2 < 0){
                    System.out.println("Escolha invalida. Tente novamente.");
                    System.out.println("1 = Criar um pedido.\n2 = Consultar um pedido ");
                    choice2 = scanner.nextInt();
                }

                if (choice2 == 1) {
                    Pedido pedido = new Pedido();
                    ItemPedidoUseCase it = new ItemPedidoUseCase();
                    List<ItemPedido> list = new ArrayList<>();
                    iniciarPedido(pedido, list);
                    System.out.println("Deseja comecar a inserir produtos no seu pedido? (y/n)");
                    char cont = scanner.next().charAt(0);
                    while (cont != 'y' && cont != 'n') {
                        System.out.println("Escolha invalida. Tente novamente");
                        cont = scanner.next().charAt(0);
                    }
                    while (cont != 'n') {
                        criarItemPedido(pedido.getIdpedido(), list);
                        System.out.println("\nValor atual do seu pedido:" + it.totalValor(pedido) + "\n");
                        pd.mostrarCarrinho(pedido);
                        System.out.println("Deseja continuar a inserir produtos no seu pedido? (y/n)");
                        cont = scanner.next().charAt(0);
                    }
                    System.out.println("Pedido finalizado!");

                } else if (choice2 == 2) {
                    consultarPedido();
                }
            }
            // cliente
            else if (choice == 3) {
                ClienteUseCase cliente = new ClienteUseCase();
                System.out.println("Deseja criar, consultar um cliente por nome, ou consultar todos os clientes? (1, 2, 3)");
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

            System.out.println("Deseja continuar? (y/n)");
            char continuar = scanner.next().charAt(0);
            while (continuar != 'n' && continuar != 'y') {
                System.out.println("Escolha invalida tente novamente (y/n).");
                continuar = scanner.next().charAt(0);
            }
            if (continuar == 'n') {
                choice = 0;
            }
            else if (continuar == 'y'){
                System.out.println("Qual tabela deseja manipular?");
                System.out.println("1 = Produto\n2 = Pedido\n3 = Cliente\n0 = Finalizar programa.");
                choice = scanner.nextInt();
            }
            if (choice == 321) {
                ClienteUseCase c = new ClienteUseCase();
                System.out.println("Tem certeza que quer formatar o banco de dados? (y/n)");
                char escolha = scanner.next().charAt(0);
                if (escolha == 'y') {
                    c.formatarBanco();
                }
                else {
                    System.exit(0);
                }
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


    public void iniciarPedido(Pedido ped, List<ItemPedido> ip) {
        PedidoUseCase pd = new PedidoUseCase();
        ClienteUseCase cliente = new ClienteUseCase();
        System.out.println("Insira o nome do cliente. (Caso nao tenha o nome de um cliente, abra o programa novamente e crie um.)");
        scanner.nextLine();
        String nome = scanner.nextLine();
        ped.setIdcliente(cliente.consultarIDclientePorNome(nome));
        pd.criarPedidoSemTotal(ped);
        ped.setItensPedido(ip);
    }


    public void criarItemPedido(int idpedido, List<ItemPedido> ip) {
        ItemPedidoUseCase it = new ItemPedidoUseCase();
        ProdutoUseCase pr = new ProdutoUseCase();
        System.out.println("Tabela de Produtos disponiveis:");
        pr.consultarProdutos();
        scanner.nextLine();
        System.out.println("Agora insira o ID do produto ao seu pedido.");
        int ID = scanner.nextInt();
        int i = pr.consultarIDItem(ID);
        while (i == 0) {
            System.out.println("Insira o produto novamente.");
            ID = scanner.nextInt();
            i = pr.consultarIDItem(ID);
        }
        System.out.println("Quantos deste produto deseja comprar?");
        int qt = scanner.nextInt();
        while (qt < 1 || qt > 99) {
            System.out.println("Quantidade invalida. Tente novamente.");
            qt = scanner.nextInt();
        }

        ItemPedido itpedido = ItemPedido.builder().idpedido(idpedido).quantidade(qt).idproduto(i).build();
        ip.add(itpedido);
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
        if (pd.consultarPedido(nome) == null) {
            System.out.println("Nenhum pedido associado ao cliente '" + nome + "' foi encontrado.");
        }
        else {
            System.out.println("Caso queira ver quais produtos foram comprados em cada pedido. Insira o ID do pedido. (0 para cancelar.)");
            int idpedido = scanner.nextInt();
            pd.printarLista(idpedido);
        }


    }


}
