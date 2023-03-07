package com.example.mercado.consumer;

import com.ada.mercado.Compra;
import com.ada.mercado.Produto;
import com.example.mercado.repositories.CompraRepository;
import com.example.mercado.repositories.ProdutoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
@KafkaListener(topics = "${topic.name}")
public class CompraConsumer {
    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;
    @KafkaHandler
    public void consumer(ConsumerRecord<String, Compra> record, Acknowledgment ack) {
        var compra = record.value();
        log.info("Mensagem consumida " + compra.toString());
        var compraEntity = com.example.mercado.entity.Compra.builder().build();
        compraEntity.setIdCompra(compra.getIdCompra().toString());
        compraEntity.setDataCompra(LocalDateTime.now());
        compraEntity.setCpf(compra.getCpf().toString());
        compra.getProdutosCompradosList()
                .forEach(p -> produtoRepository
                        .findByNome(p.toString())
                        .ifPresent(produto -> compraEntity.getProdutosCompradosList()
                                .add(produto)));
        compraRepository.save(compraEntity);
        ack.acknowledge();
    }
    @KafkaHandler
    public void consumerProduto(Produto produto, Acknowledgment ack){
        log.info("Mensagem consumida" + produto);
//        var produtoEntity = com.example.mercado.entity.Produto.builder().build();
//        produtoEntity.setIdProduto((produto.getIdProduto().toString()));
//        produtoEntity.setNome(produto.getNome().toString());
//        produtoEntity.setQuantidade(produto.getQuantidade());
//        produtoEntity.setPreco(produto.getPreco());
//        produtoRepository.save(produtoEntity);
        ack.acknowledge();
    }
    @KafkaHandler(isDefault = true)
    public void unknown(Object object, Acknowledgment ack){
        log.info("Mensagem Received " + object);
        ack.acknowledge();
    }
}
