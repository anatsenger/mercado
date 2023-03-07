package com.example.mercado.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProdutoDTO {
    private String idProduto;
    private String nome;
    private Integer quantidade;
    private Double preco;
}
