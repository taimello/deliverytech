package com.deliverytech.delivery_api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoDTO {
    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    @Max(value = 10, message = "Quantidade deve ser no máximo 10")
    private Integer quantidade;

    //Getters e Setters
    public Long getProdutoId(){
        return produtoId;
    }

    public void setProdutoId(Long produtoId){
        this.produtoId = produtoId;
    }

    public Integer getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(Integer quantidade){
        this.quantidade = quantidade;
    }
}
