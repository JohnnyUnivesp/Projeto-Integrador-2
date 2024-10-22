package br.com.sparkcommerce.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.Produto;

@Controller
@Path("produtos")
public class ProdutosController {
	
	@Inject Result result;
	@Inject ProdutoDAO produtoDao;
	@Inject DAO<Categoria> categoriaDao;
	
	@IncludeParameters
	@Get("")
	public void produtos(Produto filtro) {

		result.include("categorias",categoriaDao.selectAll());
			
		if(filtro != null) {
			result.include("produtos",produtoDao.filter(filtro));
		}
		else {
			result.include("produtos",produtoDao.selectAll());
			}
	}

}
