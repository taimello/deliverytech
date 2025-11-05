package com.deliverytech.delivery_api.dto.request;

import com.deliverytech.delivery_api.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public class StatusPedidoDTO {

    @NotNull(message = "O Id do pedido é obrigatório.")
    private Long pedidoId;

    @NotNull(message = "O status do pedido é obrigatório.")
    private StatusPedido status;

    //Getters e Setters
    public Long getPedidoId(){
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId){
        this.pedidoId = pedidoId;
    }

    public StatusPedido getStatus(){
        return status;
    }

    public void setStatus(StatusPedido status){
        this.status = status;
    }
}
