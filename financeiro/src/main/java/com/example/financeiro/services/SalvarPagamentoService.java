package com.example.financeiro.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.example.financeiro.entity.Fornecedor;
import com.example.financeiro.entity.Item;
import com.example.financeiro.entity.Pagamento;
import com.example.financeiro.entity.Produto;
import com.example.financeiro.payload.request.ItemRequest;
import com.example.financeiro.payload.request.PagamentoFornecedorRequest;
import com.example.financeiro.repositories.PagamentoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalvarPagamentoService {

    private final RetrieveOrCreateProdutoService retrieveOrCreateProdutoService;
    private final RetrieveOrSaveFornecedorService retrieveOrSaveFornecedorService;

    private final PagamentoRepository pagamentoRepository;

    @CacheEvict(cacheNames = "rlfornecedor", allEntries = true)
    public void execute(PagamentoFornecedorRequest pagamentoFornecedorRequest){
        Fornecedor fornecedor = new Fornecedor();
        BeanUtils.copyProperties(pagamentoFornecedorRequest.getFornecedor(),fornecedor);
        List<Item> items = pagamentoFornecedorRequest.getItems().stream().map(this::getItems)
                .collect(Collectors.toList());

        Fornecedor fornecedorSaved = retrieveOrSaveFornecedorService.execute(fornecedor);

        Pagamento pagamento = new Pagamento();
        pagamento.setFornecedor(fornecedorSaved);
        pagamento.adicionarItems(items);
        pagamentoRepository.save(pagamento);
    }

    private Item getItems(ItemRequest itemRequest) {
        Item item = new Item();
        BeanUtils.copyProperties(itemRequest, item);
        Produto produto = new Produto();
        BeanUtils.copyProperties(itemRequest.getProduto(), produto);
        item.setProduto(retrieveOrCreateProdutoService.execute(produto));
        return item;
    }
}