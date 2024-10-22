package br.com.sparkcommerce.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.sparkcommerce.dao.ProdutoDAO;
import br.com.sparkcommerce.model.Carrinho;
import br.com.sparkcommerce.model.ItemCarrinho;
import br.com.sparkcommerce.model.Produto;

@Controller
@Path("carrinho")
public class CarrinhoController {

	@Inject ProdutoDAO produtoDao;
    @Inject private Result result;
    @Inject HttpSession session;

    @Post("adicionar")
    public void adicionar(int produtoId, int quantidade) {
    	Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");
    	if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho); // Armazena o carrinho na sessão
        }
        Produto produtoDoBanco = produtoDao.selectPorId(produtoId); // Buscar produto do banco
        carrinho.adicionarItem(produtoDoBanco, quantidade);
        result.redirectTo(this).verCarrinho();
    }

    @Get("")
    public void verCarrinho() {
    	
    	Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        // Garante que o carrinho esteja disponível
        if (carrinho == null) {
            carrinho = new Carrinho();
            session.setAttribute("carrinho", carrinho);
        }
     // Calcular o preço total por item e o subtotal para o carrinho
        double subtotal = 0;
        for (ItemCarrinho item : carrinho.getItens()) {
            double precoTotalItem = item.getProduto().getValor() * item.getQuantidade();
            item.setPrecoTotal(precoTotalItem); // Armazena o preço total no próprio item
            subtotal += precoTotalItem; // Soma para o subtotal
        }

        result.include("itens", carrinho.getItens());
        result.include("subtotal", subtotal);
    }

    @Post("atualizarQuantidade")
    public void atualizarQuantidade(Long produtoId, int quantidade) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        if (carrinho != null) {
            carrinho.atualizarQuantidade(produtoId, quantidade);
        }

        result.redirectTo(this).verCarrinho();
    }

    @Post("remover")
    public void remover(Long produtoId) {
        Carrinho carrinho = (Carrinho) session.getAttribute("carrinho");

        if (carrinho != null) {
            carrinho.removerItem(produtoId);
        }

        result.redirectTo(this).verCarrinho();
    }
    
}
