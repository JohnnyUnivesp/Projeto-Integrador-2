package br.com.sparkcommerce.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.olimposistema.aipa.dao.filter.FiltrableName;
import br.com.olimposistema.aipa.imagem.Imagem;
import br.com.olimposistema.aipa.model.Model;
import br.com.sparkcommerce.rn.FormataDeDoubleParaReais;

@Entity
public class Produto extends Model {
	
	@NotEmpty(message = "{produto.nome.notempty}") @Size(min = 3, max = 150, message = "{produto.nome.size}")
	@FiltrableName
	private String nome;
	
	@NotEmpty(message = "{produto.descricao.notempty}") @Type(type="text")
	private String descricao;
	
	@NotNull(message = "{produto.valor.notnull}") @Min(value=0,message = "{produto.valor.min}")
	private Double valor;
	
	@ManyToOne @NotNull(message = "{produto.categoria.notnull}")
	private Categoria categoria;
	
	@NotNull(message = "Quantidade de estoque não pode ser nula.")
	@Min(value = 1, message = "Quantidade de estoque deve ser maior que zero.")
	private Integer quantidadeEstoque;
	
	@OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
	private Imagem imagem;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}
	
	public String getValorMoney() {
		String valorFormatado = new FormataDeDoubleParaReais().executa(valor);
		return valorFormatado;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
}
