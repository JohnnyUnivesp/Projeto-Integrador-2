package br.com.sparkcommerce.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.Validator;
import br.com.olimposistema.aipa.dao.DAO;
import br.com.olimposistema.aipa.imagem.Imagem;
import br.com.olimposistema.aipa.service.Util;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.interceptors.SomenteAdmin;
import br.com.sparkcommerce.interceptors.SomenteLogado;
import br.com.sparkcommerce.model.Categoria;
import br.com.sparkcommerce.model.Produto;

@Controller
@Path("formproduto")
public class FormProdutoController {
	
	@Inject Validator validator;
	@Inject ProdutoDAO produtoDao;
	@Inject DAO<Categoria> categoriaDAO;
	@Inject DAO<Imagem> imagemDAO;
	@Inject Result result;
	
	@Get("") @SomenteAdmin
	public void formproduto(Produto produto ) {
		result.include("categorias",categoriaDAO.selectAll());
		if(Util.isNotNull(produto) && Util.isPositivo(produto.getId())) {
			Produto produtoDoBanco = produtoDao.selectPorId(produto);
			result.include("produto",produtoDoBanco);
			Imagem imagemDoBanco = imagemDAO.selectPorId(produtoDoBanco.getImagem().getId());
			result.include("imagem",imagemDoBanco);
		}
	}
	
	@IncludeParameters
	@SomenteLogado
	@Post("salvaProduto")
	public void salvaProduto(@Valid Produto produto, UploadedFile imagemFile) {
	    // Validações de erro
	    validator.onErrorRedirectTo(this).formproduto(produto);
	    
	    if (Util.isNotNull(produto.getId()) && produto.getId() > 0) { // Verifica se o produto já tem um ID (é existente)
	        // Se o produto já existe, busca a versão atual do banco de dados
	        Produto produtoDoBanco = produtoDao.selectPorId(produto);
	        
	        // Se houver uma nova imagem carregada, atualiza a imagem
	        if (imagemFile != null && !imagemFile.getFileName().isEmpty()) {
	            Imagem novaImagem = new Imagem(imagemFile); // Cria nova Imagem
	            produto.setImagem(novaImagem); // Atualiza a imagem no produto
	        } else {
	            // Mantém a imagem antiga se nenhuma nova imagem foi carregada
	            produto.setImagem(produtoDoBanco.getImagem());
	        }
	    } else {
	        // Se for um novo produto, insere a imagem nova (se fornecida)
	        if (imagemFile != null && !imagemFile.getFileName().isEmpty()) {
	            Imagem novaImagem = new Imagem(imagemFile);
	            produto.setImagem(novaImagem);
	        }
	    }

	    // Persiste o produto
	    produtoDao.insertOrUpdate(produto);
	    
	    // Redireciona para a listagem de produtos
	    result.redirectTo(ProdutosController.class).produtos(null);
	}
}