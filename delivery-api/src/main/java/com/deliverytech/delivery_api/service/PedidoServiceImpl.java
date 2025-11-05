package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.dto.request.ItemPedidoDTO;
import com.deliverytech.delivery_api.dto.request.PedidoDTO;
import com.deliverytech.delivery_api.entity.*;
import com.deliverytech.delivery_api.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.repository.*;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Criar novo pedido
     */
    @Override
    @Transactional

    public PedidoResponseDTO criarPedido(PedidoDTO dto) {
        //validar cliente existe e está ativo
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: "));
        
        if (!cliente.isAtivo()) {
            throw new BusinessException("Cliente inativo não pode fazer pedidos");
        }

        //Validar restaurante existe e está ativo
        Restaurante restaurante = restauranteRepository.findById(dto.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado"));
        if (!restaurante.isAtivo()) {
            throw new BusinessException("Restaurante não está disponivel");
        }

        //Validar todos os produtos existem e estão ativos
        List<ItemPedido> itensPedido = new ArrayList<>();
        BigDecimal subTotal = BigDecimal.ZERO;

        for(ItemPedidoDTO itemDTO:dto.getItens()){
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + itemDTO.getProdutoId()));
            
            if(!produto.isDisponivel()){
                throw new BusinessException("Produto indisponível: " + produto.getNome());
            }    

            if(!produto.getRestaurante().getId().equals(dto.getRestauranteId())){
                throw new BusinessException("Produto não pertence ao restaurante selecionado");
          }

          //Criar item do pedido
          ItemPedido item = new ItemPedido();
          item.setProduto(produto);
          item.setQuantidade(itemDTO.getQuantidade());
          item.setPrecoUnitario(produto.getPreco());
          item.setSubTotal(produto.getPreco().multiply(BigDecimal.valueOf(itemDTO.getQuantidade())));

          itensPedido.add(item);
          subTotal = subTotal.add(item.getSubTotal());
        }

        //Calcular total do pedido
        BigDecimal taxaEntrega = restaurante.getTaxaEntrega();
        BigDecimal valorTotal = subTotal.add(taxaEntrega);

        //Salvar pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setEnderecoEntrega(dto.getEnderecoEntrega());
        pedido.setSubtotal(subTotal);
        pedido.setTaxaEntrega(taxaEntrega);
        pedido.setValorTotal(valorTotal);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        //Salvar itens do pedido
        for(ItemPedido item:itensPedido){
            item.setPedido(pedidoSalvo);
        }

        pedidoSalvo.setItens(itensPedido);

        //Atualizar estoque
        //retornar pedido criado
        
        return modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);
    }

    /*
     * Adicionar intem ao pedido
    
    public Pedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + produtoId));

        if (!produto.isDisponivel()) {
            throw new IllegalArgumentException("Produto não disponivel: " + produto.getNome());
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        // Verificar se produto pertence ao mesmo restaurante do pedido
        if (!produto.getRestaurante().getId().equals(pedido.getRestaurante().getId())) {
            throw new IllegalArgumentException("Produto não pertence ao restaurante do pedido");
        }

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());
        item.calcularSubtotal();

        pedido.adicionarItem(item);

        return pedidoRepository.save(pedido);
    }
     */

    /*
     * Confirmar pedido
     
    public Pedido confirmarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new IllegalArgumentException("Apenas pedidos pendentes podem ser confirmados");
        }

        if (pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve ter pelo menos um item");
        }

        pedido.confirmar();
        return pedidoRepository.save(pedido);
    }
    */

    /*
     * Buscar por ID
     */
    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o id: " + id));

        return modelMapper.map(pedido, PedidoResponseDTO.class);
    }

    /*
     * Buscar por cliente
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId) {
        
        List<Pedido> pedidos = pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId);

        return pedidos.stream()
            .map(pedido -> modelMapper.map(pedido,PedidoResponseDTO.class))
            .collect(Collectors.toList());
    }

    /*
     * Cancelar pedido
     
    public Pedido cancelarPedido(Long pedidoId, String motivo) {
        Pedido pedido = buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new IllegalArgumentException("Pedido já entregue não pode ser cancelado");
        }
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalArgumentException("Pedido já está cancelado");

        }

        return pedidoRepository.save(pedido);

    }
    */

    /*
     * Atualizar status do pedido
     */
    public PedidoResponseDTO atualizarStatusPedido(Long pedidoId, StatusPedido novoStatus) {
        // Busca o pedido pelo ID
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

       
        //Validar transições de status permitidas
        if(!isTransicaoValida(pedido.getStatus(), novoStatus)){
            throw new BusinessException("Transição de status inválida: " + pedido.getStatus() + "->" + novoStatus);
        }
        // Atualiza o status
        pedido.setStatus(novoStatus);

        // Salva o pedido atualizado
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);

        return modelMapper.map(pedidoAtualizado, PedidoResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens){
        BigDecimal total = BigDecimal.ZERO;

        for(ItemPedidoDTO item : itens){
            Produto produto = produtoRepository.findById(item.getProdutoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            
            BigDecimal subTotalItem = produto.getPreco()
                .multiply(BigDecimal.valueOf(item.getQuantidade()));
            total = total.add(subTotalItem);
        }

        return total;
    }

    @Override
    public void cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if(!podeSerCancelado(pedido.getStatus())){
            throw new BusinessException("Pedido não pode ser cancelado no status: " + pedido.getStatus());
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    private boolean isTransicaoValida(StatusPedido statusAtual, StatusPedido novoStatus){
        switch (statusAtual) {
            case PENDENTE:
                return novoStatus == StatusPedido.CONFIRMADO || novoStatus == StatusPedido.CANCELADO;
            
            case CONFIRMADO:
                return novoStatus == StatusPedido.PREPARANDO || novoStatus == StatusPedido.CANCELADO;
            
            case PREPARANDO:
                return novoStatus == StatusPedido.SAIU_PARA_ENTREGA;
            
            case SAIU_PARA_ENTREGA:
                return novoStatus == StatusPedido.ENTREGUE;

            default:
                return false;
        }
    }

    private boolean podeSerCancelado(StatusPedido status){
        return status == StatusPedido.PENDENTE || status == StatusPedido.CONFIRMADO;
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId);
    }
    
    @Transactional(readOnly = true)
    public List<Pedido> listarPedidosDoDia() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fim = LocalDate.now().atTime(LocalTime.MAX);
        return pedidoRepository.findPedidosDoDia(inicio, fim);
    }

}
