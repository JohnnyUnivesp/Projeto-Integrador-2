package br.com.sparkcommerce.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.sparkcommerce.interceptors.SomenteLogado;
import br.com.sparkcommerce.model.Categoria;

@Controller
@Path("deletacategoria")
public class DeletaCategoriaController {
	
	@Inject DAO<Categoria> categoriaDao;
	@Inject Result result;
	
	@Get("/{categoria.id}")
	@SomenteLogado
	public void deletacategoria(Categoria categoria) {
		categoriaDao.delete(categoria);
		result.redirectTo(CategoriasController.class).categorias();
		
	}	
}
