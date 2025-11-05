package com.deliverytech.delivery_api.controller;

import java.math.BigDecimal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.dto.request.ItemPedidoDTO;
import com.deliverytech.delivery_api.dto.request.PedidoDTO;
import com.deliverytech.delivery_api.dto.request.StatusPedidoDTO;
import com.deliverytech.delivery_api.dto.response.PedidoResponseDTO;
import com.deliverytech.delivery_api.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /** 
     * Criar novo pedido 
     */ 
    @PostMapping 
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoDTO dto) {

        PedidoResponseDTO pedido = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    /** 
     * Buscar pedido por ID 
     */ 
    @GetMapping("/{id}") 
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) { 
        
        PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }


    /** 
     * Listar pedidos por cliente 
     */ 
    @GetMapping("/cliente/{clienteId}") 
    public ResponseEntity<List<PedidoResponseDTO>>buscarPorCliente(@PathVariable Long clienteId) { 
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId); 
        return ResponseEntity.ok(pedidos); 
    }

    /** 
     * Atualizar status do pedido 
     */ 
    @PutMapping("/{id}/status") 
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody StatusPedidoDTO statusDTO) { 

        PedidoResponseDTO pedido = pedidoService.atualizarStatusPedido(id, statusDTO.getStatus());
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id){
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calcular")
    public ResponseEntity<BigDecimal> calcularTotal(@Valid @RequestBody List<ItemPedidoDTO> itens){
        BigDecimal total =  pedidoService.calcularTotalPedido(itens);
        return ResponseEntity.ok(total);
    }


 
}
