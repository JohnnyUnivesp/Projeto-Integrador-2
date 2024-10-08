package br.com.sparkcommerce.controller;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.model.Produto;

@Controller
@Path("produto")
public class ProdutoController {
	@Inject ProdutoDAO produtoDao;
	@Inject Result result;
	
	@Get("")
	public void listagemDeProdutos() {
		List<Produto> produtos = produtoDao.selectAll();
		
		result.use(Results.json())
		.withoutRoot()
		.from(produtos)
		.include("imagem")
		.serialize();
	}
	
}
