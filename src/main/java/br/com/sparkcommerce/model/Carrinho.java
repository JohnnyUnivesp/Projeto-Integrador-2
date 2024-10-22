package br.com.sparkcommerce.model;

import java.util.ArrayList;
import java.util.List;

import br.com.olimposistema.aipa.model.Model;

public class Carrinho extends Model {
 private List<ItemCarrinho> itens;

 public Carrinho() {
     this.itens = new ArrayList<>();
 }

 public void adicionarItem(Produto produto, int quantidade) {
     for (ItemCarrinho item : itens) {
         if (item.getProduto().getId() == (produto.getId())) {
             item.setQuantidade(item.getQuantidade() + quantidade);
             return;
         }
     }
     this.itens.add(new ItemCarrinho(produto, quantidade));
 }

 public void removerItem(Long produtoId) {
     this.itens.removeIf(item -> item.getProduto().getId() == (produtoId));
 }

 public void atualizarQuantidade(Long produtoId, int novaQuantidade) {
     for (ItemCarrinho item : itens) {
         if (item.getProduto().getId() == (produtoId)) {
             item.setQuantidade(novaQuantidade);
             break;
         }
     }
 }

 public List<ItemCarrinho> getItens() {
     return itens;
 }
}
