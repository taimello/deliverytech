package com.deliverytech.delivery_api.service;

import java.math.BigDecimal;
import java.util.List;

import com.deliverytech.delivery_api.dto.request.ItemPedidoDTO;
import com.deliverytech.delivery_api.dto.request.PedidoDTO;
import com.deliverytech.delivery_api.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery_api.enums.StatusPedido;
public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoDTO dto);
    PedidoResponseDTO buscarPedidoPorId(Long id);
    List<PedidoResponseDTO> buscarPedidosPorCliente (Long clienteId);
    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);
    BigDecimal calcularTotalPedido(List<ItemPedidoDTO> itens);
    void cancelarPedido(Long id);
}
