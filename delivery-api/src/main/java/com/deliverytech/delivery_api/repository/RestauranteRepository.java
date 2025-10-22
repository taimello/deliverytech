package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.dto.projection.RelatorioVendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    
    //Buscar por nome
    Optional<Restaurante>findByNome(String nome);

    //Buscar restaurante ativos
    List<Restaurante>findByAtivoTrue();

    //Buscar por categoria
    List<Restaurante>findByCategoriaAndAtivoTrue(String categoria);

    //Buscar por nome contendo (case insensitive)
    List<Restaurante>findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);

    //Buscar por avaliação mínima 
    List<Restaurante>findByAvaliacaoGreaterThanEqualAndAtivoTrue(BigDecimal avaliacao);

    //Ordenar por avaliação
    List<Restaurante>findByAtivoTrueOrderByAvaliacaoDesc();

    //Query customizada - restaurantes com produtos
    @Query("SELECT DISTINCT r FROM Restaurante r JOIN r.produtos p WHERE r.ativo = true")
    List<Restaurante>findRestaurantesComProdutos();

    //Buscar por faixa de taxa de entrega
    @Query("SELECT r FROM Restaurante r WHERE r.taxaEntrega BETWEEN :min AND :max AND r.ativo = true")
    List<Restaurante>findByTaxaEntregaBetween(@Param("min")BigDecimal min, @Param("max")BigDecimal max);

    //Categorias disponíveis
    @Query("SELECT DISTINCT r.categoria FROM Restaurante r WHERE r.ativo = true ORDER BY r.categoria")
    List<String>findCategoriasDisponiveis();

    @Query("SELECT r.nome AS nomeRestaurante, SUM(p.valorTotal) AS totalVendas, COUNT(p.id) AS quantidadePedidos FROM Restaurante r LEFT JOIN Pedido p ON r.id = p.restaurante.id GROUP BY r.id, r.nome")
    List<RelatorioVendas> relatorioVendasPorRestaurantes();
}
