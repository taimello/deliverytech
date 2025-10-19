package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data

public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    // Calcula o subtotal do items
    public void calcularSubtotal() {
        if (precoUnitario != null && quantidade != null) {
            subTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        } else {
            subTotal = BigDecimal.ZERO;
        }
    }
}
