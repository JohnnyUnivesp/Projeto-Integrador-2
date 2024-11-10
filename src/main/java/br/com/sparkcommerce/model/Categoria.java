package br.com.sparkcommerce.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import br.com.olimposistema.aipa.model.Model;

@Entity
public class Categoria extends Model {
	
	@NotEmpty @Size(min = 3, max = 100, message = "{categoria.nome.size}")
	private String nome;
	
	@OneToMany(mappedBy = "categoria")
	private List<Produto> produtos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
