package com.example.mercado.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String idCompra;
    @CreatedDate
    private LocalDateTime dataCompra;
    private String cpf;
    @OneToMany(mappedBy = "nome")
    private List<Produto> produtosCompradosList;
}
