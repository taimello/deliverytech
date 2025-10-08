package com.deliverytech.delivery_api.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private boolean ativo;

    @OneToMany(mappedBy = "cliente")

    private List<Pedido> pedidos;

}
