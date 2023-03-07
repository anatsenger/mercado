package com.example.mercado.controllers;

import com.example.mercado.configuration.ProdutoDTO;
import com.example.mercado.entity.Produto;
import com.example.mercado.producer.ProdutoProducer;
import com.example.mercado.services.CreateProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {
    private final CreateProdutoService createProdutoService;

    private final ProdutoProducer produtoProducer;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Void> sendMessage(@RequestBody ProdutoDTO produtoDTO){
        var id = UUID.randomUUID().toString();

        var message = com.ada.mercado.Produto.newBuilder()

                .setIdProduto(id)
                .setNome(produtoDTO.getNome())
                .setQuantidade(produtoDTO.getQuantidade())
                .setPreco(produtoDTO.getPreco())
                .build();
        produtoProducer.sendMessage(message);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable String id){
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID não informado!");
        }
        Produto produto = createProdutoService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possivel localizar um produto com o id informado!"));
        return ResponseEntity.ok(produto);
    }

}
