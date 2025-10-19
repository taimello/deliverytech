package com.deliverytech.delivery_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataPedido;
    private String enderecoEntrega;
    private BigDecimal subtotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private Long clienteId;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    public void confirmar() {
        this.status = StatusPedido.CONFIRMADO;
    }

    public void adicionarItem(ItemPedido item) {
        // associa o item a este pedido
        item.setPedido(this);

        // adiciona o item à lista
        this.itens.add(item);

        // atualiza o subtotal com base no subTotal do item
        if (item.getSubTotal() != null) {
            this.subtotal = this.subtotal.add(item.getSubTotal());
        }

        // atualiza o valor total (subtotal + taxa de entrega)
        this.valorTotal = this.subtotal.add(this.taxaEntrega != null ? this.taxaEntrega : BigDecimal.ZERO);
    }

}
