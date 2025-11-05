package com.deliverytech.delivery_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.deliverytech.delivery_api.dto.request.ItemPedidoDTO;

public class PedidoResponseDTO {
    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String enderecoEntrega;
    private LocalDateTime dataPedido;
    private BigDecimal subTotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;

    private List<ItemPedidoDTO> itens;

    //Getters e Setters
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getClienteId(){
        return clienteId;
    }

    public void setClienteId(Long clienteId){
        this.clienteId = clienteId;
    }

    public Long getRestauranteId(){
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId){
        this.restauranteId = restauranteId;
    }

    public String getEnderecoEntrega(){
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega){
        this.enderecoEntrega = enderecoEntrega;
    }

    public LocalDateTime getDataPedido(){
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido){
        this.dataPedido = dataPedido;
    }

    public BigDecimal getSubTotal(){
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal){
        this.subTotal = subTotal;
    }

    public BigDecimal getTaxaEntrega(){
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega){
        this.taxaEntrega = taxaEntrega;
    }

    public BigDecimal getValorTotal(){
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal){
        this.valorTotal = valorTotal;
    }

    public List<ItemPedidoDTO> getItens(){
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens){
        this.itens = itens;
    }
}
