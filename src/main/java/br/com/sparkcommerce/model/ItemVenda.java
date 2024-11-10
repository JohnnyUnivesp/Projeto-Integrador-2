package br.com.sparkcommerce.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class ItemVenda {

    private Long produtoId;
    private int quantidade;
    
    @Transient
    private Produto produto;
    
    // Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    
    @Override
    public String toString() {
        return "ItemVenda{" +
                "produtoId=" + produtoId +
                ", quantidade=" + quantidade +
                '}';
    }
	public void setProduto(Produto produto) {
		this.produto = produto;		
	}
}
