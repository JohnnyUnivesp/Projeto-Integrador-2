package br.com.sparkcommerce.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.Validator;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.interceptors.SomenteLogado;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.Produto;

@Controller
@Path("formproduto")
public class FormProdutoController {
	
	@Inject Validator validator;
	@Inject ProdutoDAO produtoDao;
	@Inject DAO<Categoria> categoriaDAO;
	@Inject Result result;
	
	@Get("") @SomenteLogado
	public void formproduto( ) {
		result.include("categorias",categoriaDAO.selectAll());
	}
	
	@IncludeParameters
	@SomenteLogado
	@Post("salvaProduto")
	
	public void salvaProduto(@Valid Produto produto) {
		
		validator.onErrorRedirectTo(this).formproduto();
		produtoDao.insertOrUpdate(produto);
		result.redirectTo(ProdutosController.class).produtos(null);
		
	}

}
