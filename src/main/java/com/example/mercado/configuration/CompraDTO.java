package com.example.mercado.configuration;


import com.example.mercado.entity.Compra;
import com.example.mercado.repositories.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
public class CompraDTO {
    private String idCompra;
    private String cpf;
    private List<String> produtosCompradosList;

}
