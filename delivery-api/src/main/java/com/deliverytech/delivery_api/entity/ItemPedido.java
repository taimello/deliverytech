package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data

public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
