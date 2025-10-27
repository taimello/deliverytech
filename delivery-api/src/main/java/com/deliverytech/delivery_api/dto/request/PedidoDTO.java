package com.deliverytech.delivery_api.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PedidoDTO {
    
    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "Restaurante é obrigatório")
    private Long restauranteId;

    @NotBlank(message = "Endereço de entrega é obrigatório")
    @Size(max = 200, message = "Endereço deve ter no máximo 200 carcateres")
    private String enderecoEntrega;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    @Valid
    private List<ItemPedidoDTO> itens;

    //Getters e Setters
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

    public List<ItemPedidoDTO> getItens(){
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens){
        this.itens = itens;
    }
}
