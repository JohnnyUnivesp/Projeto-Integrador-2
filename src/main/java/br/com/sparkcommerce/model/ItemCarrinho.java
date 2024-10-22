package br.com.sparkcommerce.model;

public class ItemCarrinho {
    private Produto produto;
    private int quantidade;
	private Object precoTotal;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

	public void setPrecoTotal(double precoTotalItem) {
		this.precoTotal = precoTotalItem;
	}

	public Object getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Object precoTotal) {
		this.precoTotal = precoTotal;
	}
	
}