package br.com.sparkcommerce.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.interceptors.SomenteAdmin;
import br.com.sparkcommerce.model.Produto;

@Controller
@Path("deletaproduto")
public class DeletaProdutoController {
	
	@Inject ProdutoDAO produtoDao;
	@Inject Result result;
	
	@Get("/{produto.id}")
	@SomenteAdmin
	public void deletaproduto(Produto produto) {
		produtoDao.delete(produto);
		result.redirectTo(ProdutosController.class).produtos(null);
		
	}	
}
