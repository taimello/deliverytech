package com.deliverytech.delivery_api.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.repository.PedidoRepository;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run (String... args) throws Exception{
        System.out.println("=== INICIANDO CARGA DOS TESTES ===");
    

    //Limpar dados existentes
    pedidoRepository.deleteAll();
    produtoRepository.deleteAll();
    restauranteRepository.deleteAll();
    clienteRepository.deleteAll();

    //Inserir dados de teste
    inserirClientes();
    inserirRestaurantes();
    inserirProdutos();
    inserirPedidos();

    //Executar teste de consulta
    testarConsultas();

    System.out.println("=== CARGA DE DADOS CONCLUIDA ===");

    }
    
    private void inserirClientes(){
        System.out.println("---- Inserindo clientes ---");

        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("11999999999");
        cliente1.setEndereco("Rua A, 123");
        cliente1.setAtivo(true);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("129999999");
        cliente2.setEndereco("Rua B, 456");
        cliente2.setAtivo(true);

        Cliente cliente3 = new Cliente();
        cliente3.setNome("Pedro");
        cliente3.setEmail("pedro@email.com");
        cliente3.setTelefone("1122222222");
        cliente3.setEndereco("Rua C, 789");
        cliente3.setAtivo(true);

        clienteRepository.saveAll(Arrays.asList(cliente1,cliente2,cliente3));
        System.out.println("3 clientes salvos");
    }

    private void inserirRestaurantes(){
        System.out.println("--- Inserindo restaurantes ---");

        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Pizza Express");
        restaurante1.setCategoria("Italiana");
        restaurante1.setEndereco("Av principal, 100");
        restaurante1.setTelefone("1235556555");
        restaurante1.setTaxaEntrega(new BigDecimal("3.50"));
        restaurante1.setAtivo(true);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Big Burguer");
        restaurante2.setCategoria("Lanches");
        restaurante2.setEndereco("Av Dois, 133");
        restaurante2.setTelefone("1155658778");
        restaurante2.setTaxaEntrega(new BigDecimal("2.50"));
        restaurante2.setAtivo(true);

        Restaurante restaurante3 = new Restaurante();
        restaurante3.setNome("Acaiteira");
        restaurante3.setCategoria("Açai");
        restaurante3.setEndereco("Av Alameda, 1547");
        restaurante3.setTelefone("1122222222");
        restaurante3.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante3.setAtivo(true);

        restauranteRepository.saveAll(Arrays.asList(restaurante1, restaurante2, restaurante3));
        System.out.println("3 restaurantes salvos");
    }
    public void inserirProdutos(){
        System.out.println("--- Inserindo produtos ---");
        
        Produto produto1 = new Produto();
        produto1.setNome("Hambuguer");
        produto1.setDescricao("Pao, hamburguer, salada e queijo");
        produto1.setPreco(new BigDecimal("32.85"));
        produto1.setCategoria("Lanches");
        produto1.setDisponivel(true);

        Produto produto2 = new Produto();
        produto2.setNome("Pizza");
        produto2.setDescricao("Queijo e manjericao");
        produto2.setPreco(new BigDecimal("58.98"));
        produto2.setCategoria("Pizzas");
        produto2.setDisponivel(true);

        Produto produto3 = new Produto();
        produto3.setNome("Açai");
        produto3.setDescricao("Puro");
        produto3.setPreco(new BigDecimal("10.00"));
        produto3.setCategoria("Açai");
        produto3.setDisponivel(true);

        produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
        System.out.println("3 produtos salvos");
    }

    public void inserirPedidos(){
        System.out.println("--- Inserindo pedidos ---");
        
        Pedido pedido1 = new Pedido();
        pedido1.setDataPedido(LocalDateTime.now());
        pedido1.setEnderecoEntrega("Rua Alameda, 123");
        pedido1.setSubtotal(new BigDecimal("32.85"));
        pedido1.setTaxaEntrega(new BigDecimal("2.00"));
        pedido1.setValorTotal(new BigDecimal("34.85"));
        pedido1.setNumeroPedido("FDRG25648");

        Pedido pedido2 = new Pedido();
        pedido2.setDataPedido(LocalDateTime.now());
        pedido2.setEnderecoEntrega("Rua tributo, 123");
        pedido2.setSubtotal(new BigDecimal("15.85"));
        pedido2.setTaxaEntrega(new BigDecimal("2.00"));
        pedido2.setValorTotal(new BigDecimal("17.85"));
        pedido2.setNumeroPedido("FDRG5264");
        
        Pedido pedido3 = new Pedido();
        pedido3.setDataPedido(LocalDateTime.now());
        pedido3.setEnderecoEntrega("Rua cinco, 123");
        pedido3.setSubtotal(new BigDecimal("22.00"));
        pedido3.setTaxaEntrega(new BigDecimal("2.00"));
        pedido3.setValorTotal(new BigDecimal("24.00"));
        pedido3.setNumeroPedido("1123");

        pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2, pedido3));
        System.out.println("3 pedidos salvos");
    }

    public void testarConsultas(){
        System.out.println("--- TESTANDO CONSULTA DOS REPOSITORIES ---");
        
        //Teste ClienteRepository
        System.out.println("\n --- Testes cliente repository ---");

        var clientePorEmail = clienteRepository.findByEmail("joao@email.com");
        System.out.println("Cliente por email: " + (clientePorEmail.isPresent() ? clientePorEmail.get().getNome(): "Não encontrado"));

        var clientesAtivos = clienteRepository.findByAtivoTrue();
        System.out.println("Clientes ativos: " + clientesAtivos.size());

        var clientesPorNome = clienteRepository.findByNomeContainingIgnoreCase("silva");
        System.out.println("Cliente com 'Silva' no nome: " + clientesPorNome.size());

        boolean emailExiste = clienteRepository.existsByEmail("maria@email.com");
        System.out.println("Email maria@email.com existe: " + emailExiste);


    }
}
